package linalg.algorithms;

import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import linalg.matrix.MatrixFactory;
import linalg.vector.DenseVectorBuilder;
import linalg.vector.Vector;
import linalg.vector.VectorFactory;

public class GivensRotation implements QRDecomposition {

  private Matrix Q;
  private Matrix R;

  @Override
  public Matrix GetQ() {
    return Q;
  }

  @Override
  public Matrix GetR() {
    return R;
  }

  @Override
  public void Decompose(Matrix matrix) {
    this.R = matrix;
    Q = MatrixFactory.IdentityMatrix(R.Rows());
    GivensRotationAlgorithm();
    Q = Q.Transpose();
  }

  private void GivensRotationAlgorithm() {
    for (int j = 0; j < R.Cols(); j++)
      for (int i = R.Rows() - 1; i > j; i--)
        if (Math.abs(R.ValueAt(i, j) - 0.0) >= 1e-3)
          Rotate(i, j);
  }

  private void Rotate(int row, int col) {
    // cos, sin and r values
    double a = R.ValueAt(row - 1, col);
    double b = R.ValueAt(row, col);
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

    // Modifying rows R
    DenseMatrixBuilder new_R = new DenseMatrixBuilder(R.Rows(), R.Cols());
    R.ForEachEntry(e -> new_R.SetValue(e.Row(), e.Col(), e.Value()));

    for (int j = 0; j < R.Cols(); j++) {
      DenseVectorBuilder vector = new DenseVectorBuilder(2);
      vector.SetValue(0, R.ValueAt(row - 1, j));
      vector.SetValue(1, R.ValueAt(row, j));

      Vector res = VectorFactory.MatrixByVector(mat, vector.BuildVector());
      new_R.SetValue(row - 1, j, res.ValueAt(0));
      new_R.SetValue(row, j, res.ValueAt(1));
    }

    // Modifying rows Q
    DenseMatrixBuilder new_Q = new DenseMatrixBuilder(Q.Rows(), Q.Cols());
    Q.ForEachEntry(e -> new_Q.SetValue(e.Row(), e.Col(), e.Value()));

    for (int j = 0; j < Q.Cols(); j++) {
      DenseVectorBuilder vector = new DenseVectorBuilder(2);
      vector.SetValue(0, Q.ValueAt(row - 1, j));
      vector.SetValue(1, Q.ValueAt(row, j));

      Vector res = VectorFactory.MatrixByVector(mat, vector.BuildVector());
      new_Q.SetValue(row - 1, j, res.ValueAt(0));
      new_Q.SetValue(row, j, res.ValueAt(1));
    }

    Q = new_Q.BuildMatrix();
    R = new_R.BuildMatrix();
  }
}
