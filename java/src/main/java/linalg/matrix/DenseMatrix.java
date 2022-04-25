package linalg.matrix;

import linalg.matrix.Matrix;
import linalg.vector.Vector;

import java.lang.IllegalArgumentException;
import java.util.function.Consumer;

public class DenseMatrix implements Matrix  {
  @Override
  public int Rows() {
    return rows_;
  }

  @Override
  public int Cols() {
    return cols_;
  }

  @Override
  public double ValueAt(int x, int y) {
    return mat_[x][y];
  }

  @Override 
  public void ForEachEntry(Consumer<MatrixEntry> func) {
    for (int i = 0; i < Rows(); i++) {
      for (int j = 0; j < Cols(); j++) {
        func.accept(new MatrixEntry(i, j, mat_[i][j]));
      }
    }
  }

  // inside package only, to create a matrix use MatrixFactory
  DenseMatrix(int rows, int cols) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Invalid number of rows or columns");
    }
    rows_ = rows;
    cols_ = cols;
    mat_ = new double[rows_][cols_];
  }

  DenseMatrix(double[][] mat) {
    rows_ = mat.length;
    cols_ = mat[0].length;
    mat_ = mat;
  }

  double[][] mat_;
  int rows_, cols_;
}