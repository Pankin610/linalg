package linalg.matrix;

import linalg.matrix.Matrix;
import linalg.matrix.SparseMatrix;
import linalg.matrix.DenseMatrix;
import linalg.matrix.COOMatrix;
import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.COOMatrixBuilder;
import linalg.matrix.MatrixEntry;
import linalg.matrix.MatrixBuilder;


import java.lang.IllegalArgumentException;
import java.util.function.Consumer;

public class MatrixFactory {
  public static Matrix Add(Matrix a, Matrix b) {
    if (a.Rows() != b.Rows() || a.Cols() != b.Cols()) {
      throw new IllegalArgumentException("Matrix dimensions don't add up.");
    }

    if (a instanceof DenseMatrix || b instanceof DenseMatrix) {
      return AddWithBuilder(a, b, new DenseMatrixBuilder(a.Rows(), a.Cols()));
    }
    else {
      return AddWithBuilder(a, b, new COOMatrixBuilder(a.Rows(), a.Cols()));
    }
  }

  private static Matrix AddWithBuilder(Matrix a, Matrix b, MatrixBuilder builder) {    
    Consumer<MatrixEntry> add_mat = (entry -> {
      int x = entry.Row();
      int y = entry.Col();
      builder.SetValue(x, y, builder.GetValue(x, y) + entry.Value());
    });
    a.ForEachEntry(add_mat);
    b.ForEachEntry(add_mat);

    return builder.BuildMatrix();
  }

  public static DenseMatrix DenseMultiply(Matrix a, Matrix b) {
    if (a.Cols() != b.Rows()) {
      throw new IllegalArgumentException("Matrix dimensions don't add up.");
    }
    DenseMatrixBuilder builder = new DenseMatrixBuilder(a.Rows(), b.Cols());
    for (int i = 0; i < a.Rows(); i++) {
      for (int j = 0; j < b.Cols(); j++) {
        // for less builder calls which might be slow
        double value_sum = 0.;
        for (int k = 0; k < a.Cols(); k++) {
          value_sum += a.ValueAt(i, k) * b.ValueAt(k, j);
        }
        builder.SetValue(i, j, value_sum);
      }
    }

    return (DenseMatrix)builder.BuildMatrix();
  }

  public static DenseMatrix SparseByDenseMultiply(SparseMatrix sparse, Matrix dense) {
    if (sparse.Cols() != dense.Rows()) {
      throw new IllegalArgumentException("Matrix dimensions don't add up.");
    }
    DenseMatrixBuilder builder = new DenseMatrixBuilder(sparse.Rows(), dense.Cols());
    sparse.ForEachEntry(entry -> {
      int x = entry.Row();
      int y = entry.Col();
      double val = entry.Value();
      for (int i = 0; i < dense.Cols(); i++) {
        builder.SetValue(x, i, builder.GetValue(x, i) + val * dense.ValueAt(y, i));
      }
    });
    return (DenseMatrix)builder.BuildMatrix();
  }

  public static SparseMatrix SparseMultiply(SparseMatrix a, SparseMatrix b) {
    if (a.Cols() != b.Rows()) {
      throw new IllegalArgumentException("Matrix dimensions don't add up.");
    }
    COOMatrixBuilder builder = new COOMatrixBuilder(a.Rows(), b.Cols());
    a.ForEachEntry(entry -> {
      int row = entry.Row();
      int col = entry.Col();
      double value = entry.Value();
      for (int i = 0; i < b.Cols(); i++) {
        builder.SetValue(row, i, builder.GetValue(row, i) + value * b.ValueAt(col, i));
      }
    });
    return (SparseMatrix)builder.BuildMatrix();
  }

  public static Matrix IdentityMatrix(int size) {
    DenseMatrixBuilder builder = new DenseMatrixBuilder(size, size);
    for (int i = 0; i < size; i++) {
      builder.SetValue(i, i, 1.0);
    }
    return builder.BuildMatrix();
  }
}