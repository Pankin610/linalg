package linalg.matrix;

import linalg.matrix.DenseMatrix;
import linalg.matrix.MatrixBuilder;

public class DenseMatrixBuilder implements MatrixBuilder {
  double[][] mat_array_;

  public DenseMatrixBuilder(int rows, int cols) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Wrong matrix dimensions");
    }
    mat_array_ = new double[rows][cols];
  }

  // TODO extra methods and error-pruning
  @Override
  public void SetValue(int row, int col, double value) {
    mat_array_[row][col] = value;
  }

  @Override
  public double GetValue(int row, int col) {
    return mat_array_[row][col];
  }

  @Override
  public Matrix BuildMatrix() {
    return new DenseMatrix(mat_array_);
  }
};