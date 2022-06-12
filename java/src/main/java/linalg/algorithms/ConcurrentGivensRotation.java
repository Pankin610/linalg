package linalg.algorithms;

import javafx.scene.transform.Rotate;
import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import linalg.vector.DenseVectorBuilder;
import linalg.vector.Vector;
import linalg.vector.VectorFactory;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentGivensRotation implements QRDecomposition {

  @Override
  public Matrix GetQ() {
    return Q;
  }

  @Override
  public Matrix GetR() {
    return R;
  }

  @Override
  public void Decompose(Matrix matrix) throws InterruptedException, ExecutionException {
    this.R = matrix;
    GivensRotationAlgorithm();

    // filling builder with future entries
    builder_R = new DenseMatrixBuilder(R.Rows(), R.Cols());
    for (int i = 0; i < R.Rows(); i++)
      for (int j = 0; j < R.Cols(); j++)
        builder_R.SetValue(i, j, futuresR.get(i).get(j).get());

    // filling builder with future entries
    builder_Q = new DenseMatrixBuilder(R.Rows(), R.Rows());
    for (int i = 0; i < R.Rows(); i++)
      for (int j = 0; j < R.Rows(); j++)
        builder_Q.SetValue(i, j, futuresQ.get(i).get(j).get());

    R = builder_R.BuildMatrix();
    Q = builder_Q.BuildMatrix().Transpose();
  }

  private void GivensRotationAlgorithm() throws InterruptedException, ExecutionException {
    // filling futuresR with R matrix entries
    futuresR = new ArrayList<>();
    for (int i = 0; i < R.Rows(); i++) {
      ArrayList<Future<Double>> row = new ArrayList<>();
      for (int j = 0; j < R.Cols(); j++)
        row.add(executor.submit(() -> 0.0));
      futuresR.add(row);
    }
    R.ForEachEntry(e -> futuresR.get(e.Row()).set(e.Col(), executor.submit(e::Value)));

    // futuresQ := identity
    futuresQ = new ArrayList<>();
    for (int i = 0; i < R.Rows(); i++) {
      ArrayList<Future<Double>> row = new ArrayList<>();
      for (int j = 0; j < R.Rows(); j++)
        row.add(executor.submit(() -> 0.0));
      futuresQ.add(row);
    }
    for (int i = 0; i < R.Rows(); i++)
      futuresQ.get(i).set(i, executor.submit(() -> 1.0));

    for (int j = 0; j < R.Cols(); j++)
      for (int i = R.Rows() - 1; i > j; i--)
        if (Math.abs(futuresR.get(i).get(j).get() - 0.0) > 1e-4)
          Rotate(i, j);
  }

  private void Rotate(int row, int col) throws ExecutionException, InterruptedException {
    // cos, sin and r values
    double a = futuresR.get(row - 1).get(col).get();
    double b = futuresR.get(row).get(col).get();
    double r = Math.sqrt(a * a + b * b);
    double c = a / r;
    double s = -b / r;

    // rotation matrix 2x2
    DenseMatrixBuilder two_x_two = new DenseMatrixBuilder(2, 2);
    two_x_two.SetValue(0, 0, c);
    two_x_two.SetValue(0, 1, -s);
    two_x_two.SetValue(1, 0, s);
    two_x_two.SetValue(1, 1, c);

    Matrix mat = two_x_two.BuildMatrix();

    ModifyMatrixBuilderRows(futuresR, row, R.Cols(), mat);
    ModifyMatrixBuilderRows(futuresQ, row, R.Rows(), mat);
  }

  private void ModifyMatrixBuilderRows(ArrayList<ArrayList<Future<Double>>> builder, int row, int cols, Matrix mat) throws ExecutionException, InterruptedException {
    for (int j = 0; j < cols; j++) {
      DenseVectorBuilder vector = new DenseVectorBuilder(2);
      vector.SetValue(0, builder.get(row - 1).get(j).get());
      vector.SetValue(1, builder.get(row).get(j).get());

      Vector res = VectorFactory.MatrixByVector(mat, vector.BuildVector());
      builder.get(row - 1).set(j, executor.submit(() -> res.ValueAt(0)));
      builder.get(row).set(j, executor.submit(() -> res.ValueAt(1)));
    }
  }

  private Matrix Q;
  private Matrix R;
  private DenseMatrixBuilder builder_Q;
  private DenseMatrixBuilder builder_R;
  private ArrayList<ArrayList<Future<Double>>> futuresR;
  private ArrayList<ArrayList<Future<Double>>> futuresQ;
  private ExecutorService executor = Executors.newCachedThreadPool();
}
