package linalg.algorithms.concurrency;

import linalg.matrix.*;
import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import static linalg.matrix.MatrixUtils.GetMatrixEntriesByRow;

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

  public static SparseMatrix SparseMatrixProduct(Matrix a, Matrix b) throws Exception {
    if (a.Cols() != b.Rows()) {
      throw new IllegalArgumentException("Matrix dimensions don't add up.");
    }

    ArrayList<ArrayList<MatrixEntry>> left_matrix_entries_by_row =
      GetMatrixEntriesByRow(a);
    ArrayList<ArrayList<MatrixEntry>> right_matrix_entries_by_row =
      GetMatrixEntriesByRow(b);

    // Create the futures for each row.
    ArrayList<Future<Map<Integer, Double>>> row_futures = new ArrayList<>();
    for (int row = 0; row < a.Rows(); row++) {
      row_futures.add(GetRowFromMatrixProduct(
        left_matrix_entries_by_row.get(row),
        right_matrix_entries_by_row,
        row
      ));
    }

    // Use the independantly computed rows to get the final product
    COOMatrixBuilder builder = new COOMatrixBuilder(a.Rows(), b.Cols());
    for (int row = 0; row < a.Rows(); row++) {
      for (Map.Entry<Integer, Double> entry : row_futures.get(row).get().entrySet()) {
        builder.SetValue(row, entry.getKey(), entry.getValue());
      }
    }

    return (SparseMatrix)builder.BuildMatrix();
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

  private static Future<Map<Integer, Double>> GetRowFromMatrixProduct(
    ArrayList<MatrixEntry> row_entries, 
    ArrayList<ArrayList<MatrixEntry>> right_mat_entries_by_row, 
    int row) 
  {
    return executor_.submit(() -> {
      Map<Integer, Double> final_row = new HashMap<Integer, Double>();
      for (MatrixEntry left_entry : row_entries) {
        for (MatrixEntry right_entry : right_mat_entries_by_row.get(left_entry.Col())) {
          int col = right_entry.Col();
          final_row.put(col, 
            final_row.getOrDefault(col, 0.) + left_entry.Value() * right_entry.Value());
        }
      }
      return final_row;
    });
  }

  private static ExecutorService executor_ = Executors.newCachedThreadPool();
}