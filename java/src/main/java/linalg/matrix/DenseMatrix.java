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

  @Override
  public Matrix Transpose() {
    double[][] transposed = new double[Cols()][Rows()];
    for (int i = 0; i < Rows(); i++) {
      for (int j = 0; j < Cols(); j++) {
        transposed[j][i] = ValueAt(i, j);
      }
    }
    return new DenseMatrix(transposed);
  }

  @Override
  public int NumActiveEntries() {
    return Rows() * Cols();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < Rows(); i++) {
      for (int j = 0; j < Cols(); j++) {
        builder.append(ValueAt(i, j));
        builder.append(" ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  @Override 
  public boolean equals(Object o) {
    if (!(o instanceof DenseMatrix)) {
      return false;
    }

    DenseMatrix other = (DenseMatrix)o;
    if (other.Cols() != Cols() || other.Rows() != Rows()) {
      return false;
    }
    for (int i = 0; i < Rows(); i++) {
      for (int j = 0; j < Cols(); j++) {
        if (Math.abs(ValueAt(i, j) - other.ValueAt(i, j)) > 1e-9) {
          return false;
        }
      }
    }
    return true;
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