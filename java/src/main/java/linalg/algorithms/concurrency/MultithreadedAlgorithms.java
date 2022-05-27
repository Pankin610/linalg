package linalg.algorithms.concurrency;

import linalg.matrix.*;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Map;

// A collection of Linalg algorithms that utilize multithreading.
public class MultithreadedAlgorithms {
  public static DenseMatrix DenseMatrixProduct(Matrix a, Matrix b) throws Exception {
    if (a.Cols() != b.Rows()) {
      throw new IllegalArgumentException("Matrix dimensions don't add up.");
    }

    // Create the futures first.
    Map<MatrixCoordinate, Future<Double>> entry_futures = new HashMap<>();
    for (int row = 0; row < a.Rows(); row++) {
      for (int col = 0; col < b.Cols(); col++) {
        entry_futures.put(new MatrixCoordinate(row, col),   
                          ComputeMatrixEntry(a, b, row, col));
      }
    }

    // Wait for all the future entries.
    DenseMatrixBuilder builder = new DenseMatrixBuilder(a.Rows(), b.Cols());
    for (int row = 0; row < a.Rows(); row++) {
      for (int col = 0; col < b.Cols(); col++) {
        builder.SetValue(row, col, 
                         entry_futures.get(new MatrixCoordinate(row, col)).get());
      }
    }
    return (DenseMatrix)builder.BuildMatrix();
  }

  private static Future<Double> ComputeMatrixEntry(Matrix a, Matrix b, int row, int col) {
    return executor_.submit(() -> {
      double sum = 0.;
      for (int i = 0; i < a.Cols(); i++) {
        sum += a.ValueAt(row, i) * b.ValueAt(i, col);
      }
      return sum;
    });
  }

  private static ExecutorService executor_ = Executors.newCachedThreadPool();
}