package linalg.matrix;

import linalg.matrix.Matrix;
import linalg.vector.Vector;

import java.lang.IllegalArgumentException;
import java.util.function.Consumer;

public class DenseMatrix implements Matrix {
  @Override
  public int Rows() {
    return rows_;
  }

  @Override
  public int Cols() {
    return cols_;
  }

  @Override
  public Matrix Add(Matrix other) {
    if (other.Rows() != Rows() || other.Cols() != Cols()) {
      throw new IllegalArgumentException("Bad matrix dimensions for addition");
    }

    DenseMatrix new_mat = new DenseMatrix(Rows(), Cols());
    for (int i = 0; i < Rows(); i++) {
      for (int j = 0; j < Cols(); j++) {
        new_mat.Set(i, j, ValueAt(i, j) + other.ValueAt(i, j));
      }
    }
    return new_mat;
  }

  @Override
  public Matrix Multiply(Matrix other) {
    DenseMatrix new_mat = new DenseMatrix(Rows(), other.Cols());
    other.ForEachEntry((entry) -> {
      for (int k = 0; k < Rows(); k++) {
        double cur_value = new_mat.ValueAt(k, entry.Col());
        double left_value = ValueAt(k, entry.Row());
        double right_value = entry.Value();
        new_mat.Set(k, entry.Col(), cur_value + left_value * right_value);
      }
    });
    return new_mat;
  } 

  @Override
  public double ValueAt(int x, int y) {
    CheckCoords(x, y);
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
  public Vector Multiply(Vector other) {
    // TODO after VectorFactory
    return null;
  }

  double[][] mat_;
  int rows_, cols_;

  // Matrices are immutable so this is a package-only method
  void Set(int x, int y, double value) {
    CheckCoords(x, y);
    mat_[x][y] = value;
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

  private void CheckCoords(int x, int y) {
    if (x < 0 || y < 0 || x >= Rows() || y >= Cols()) {
      throw new IllegalArgumentException("Invalid coordinate");
    }
  }
}