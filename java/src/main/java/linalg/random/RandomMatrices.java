package linalg.random;

import java.util.Random;

import linalg.matrix.DenseMatrix;
import linalg.matrix.SparseMatrix;
import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.COOMatrixBuilder;
import linalg.matrix.Matrix;
import java.util.function.Supplier;

public class RandomMatrices {
  public static DenseMatrix RandomDenseMatrix(int n, int m) {
    DenseMatrixBuilder builder = new DenseMatrixBuilder(n, m);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        builder.SetValue(i, j, rng.nextDouble());
      }
    }
    return (DenseMatrix)builder.BuildMatrix();
  }

  public static Supplier<Matrix> DenseMatrixSupplier(int n, int m) {
    return () -> RandomDenseMatrix(n, m);
  }

  public static SparseMatrix RandomSparseMatrix(int n, int m, int num_entries) {
    COOMatrixBuilder builder = new COOMatrixBuilder(n, m);
    int entries_added = 0;
    while(entries_added < num_entries) {
      int i = rng.nextInt(n);
      int j = rng.nextInt(m);
      // this works fast if the required number of entries is a fraction of
      // the total number of entries, which should be the case for a sparse
      // matrix
      if (builder.GetValue(i, j) != 0.) {
        continue;
      }
      builder.SetValue(i, j, rng.nextDouble());
      entries_added++;
    }

    return (SparseMatrix)builder.BuildMatrix();
  }

  public static Supplier<Matrix> SparseMatrixSupplier(int n, int m, int num_entries) {
    return () -> RandomSparseMatrix(n, m, num_entries);
  }

  private static Random rng = new Random();
}

