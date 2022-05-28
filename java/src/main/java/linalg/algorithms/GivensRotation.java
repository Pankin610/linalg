package linalg.algorithms;

import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import linalg.matrix.MatrixFactory;

public class GivensRotation implements QRDecomposition {

  private Matrix Q;
  private Matrix R;

  @Override
  public Matrix GetQ() {
    if (Q == null)
      Q = MatrixFactory.IdentityMatrix(R.Rows());
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
  }

  private void GivensRotationAlgorithm() {
    for (int i = R.Rows() - 1; i > 0; i--)
      for (int j = 0; j < i; j++)
        if (R.ValueAt(i, j) != 0)
          R = Rotate(i, j);
  }

  private Matrix Rotate(int row, int col) {
    double r = Math.sqrt(R.ValueAt(row, col) * R.ValueAt(row, col)
        + R.ValueAt(col, col) * R.ValueAt(col, col));
    double c = R.ValueAt(col, col) / r;
    double s = -R.ValueAt(row, col) / r;

    DenseMatrixBuilder builder = new DenseMatrixBuilder(R.Rows(), R.Rows());
    for (int i = 0; i < R.Rows(); i++)
      builder.SetValue(i, i, 1.0);
    builder.SetValue(row, row, c);
    builder.SetValue(col, col, c);
    builder.SetValue(row, col, s);
    builder.SetValue(col, row, -s);
    Matrix G = builder.BuildMatrix();

    if (Q == null)
      Q = MatrixFactory.IdentityMatrix(G.Cols());
    Q = MatrixFactory.DenseMultiply(Q, G.Transpose());

    return MatrixFactory.DenseMultiply(G, R);
  }
}
