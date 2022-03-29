package linalg.matrix;

import linalg.matrix.DenseMatrix;

public class DenseMatrixBuilder {
  double[][] mat_array_;

  public DenseMatrixBuilder(int rows, int cols) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Wrong matrix dimensions");
    }
  }

  // TODO extra methods and error-pruning
  void SetValue(int row, int col, double value) {
    mat_array_[row][col] = value;
  }
};