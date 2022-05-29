package linalg.algorithms;

import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import linalg.matrix.MatrixFactory;
import linalg.vector.DenseVectorBuilder;
import linalg.vector.Vector;
import linalg.vector.VectorFactory;

public class GivensRotation implements QRDecomposition {

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
    GivensRotationAlgorithm();
    R = builder_R.BuildMatrix();
    Q = builder_Q.BuildMatrix().Transpose();
  }

  /**
   * method looks for non-zero elements in matrix builder R
   * and applies rotation on those indices
   */
  private void GivensRotationAlgorithm() {
    builder_R = new DenseMatrixBuilder(R.Rows(), R.Cols());
    R.ForEachEntry(e -> builder_R.SetValue(e.Row(), e.Col(), e.Value()));
    builder_Q = new DenseMatrixBuilder(R.Rows(), R.Rows());
    for (int i = 0; i < R.Rows(); i++)
      builder_Q.SetValue(i, i, 1.0);

    for (int j = 0; j < R.Cols(); j++)
      for (int i = R.Rows() - 1; i > j; i--)
        if (Math.abs(builder_R.GetValue(i, j) - 0.0) >= 1e-5)
          Rotate(i, j);
  }

  /**
   * creates 2x2 rotation matrix
   */
  private void Rotate(int row, int col) {
    // cos, sin and r values
    double a = builder_R.GetValue(row - 1, col);
    double b = builder_R.GetValue(row, col);
    double r = Math.sqrt(a * a + b * b);
    double c = a / r;
    double s = -b / r;

    // rotation matrix 2x2
    DenseMatrixBuilder two_x_two = new DenseMatrixBuilder(2, 2);
    two_x_two.SetValue(0, 0, c);
    two_x_two.SetValue(0, 1, -s);
    two_x_two.SetValue(1, 0, s);
    two_x_two.SetValue(1, 1, c);

    mat = two_x_two.BuildMatrix();

    ModifyMatrixBuilderRows(builder_R, row, R.Cols());
    ModifyMatrixBuilderRows(builder_Q, row, R.Rows());
  }

  /**
   *
   * @param builder in which elements should be modified by rotation
   * @param row and row-1 will be multiplied and modified
   * @param cols size of builder because DenseMatrixBuilder does not
   *             share methods .Cols or anything
   */
  private void ModifyMatrixBuilderRows(DenseMatrixBuilder builder, int row, int cols) {
    for (int j = 0; j < cols; j++) {
      DenseVectorBuilder vector = new DenseVectorBuilder(2);
      vector.SetValue(0, builder.GetValue(row - 1, j));
      vector.SetValue(1, builder.GetValue(row, j));

      Vector res = VectorFactory.MatrixByVector(mat, vector.BuildVector());
      builder.SetValue(row - 1, j, res.ValueAt(0));
      builder.SetValue(row, j, res.ValueAt(1));
    }
  }

  private Matrix Q;
  private Matrix R;
  private DenseMatrixBuilder builder_Q;
  private DenseMatrixBuilder builder_R;
  private Matrix mat; // rotation 2x2 matrix
}
