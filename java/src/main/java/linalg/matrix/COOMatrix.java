package linalg.matrix;

import linalg.matrix.Matrix;
import linalg.matrix.SparseMatrix;
import linalg.matrix.MatrixCoordinate;
import linalg.matrix.MatrixEntry;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import java.util.Map;
import java.util.function.Consumer;

// An immutable sparse matrix that stores
// its entries in the COO format
public class COOMatrix implements SparseMatrix {
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
    return entry_map_.getOrDefault(new MatrixCoordinate(x, y), 0.);
  }

  @Override
  public void ForEachEntry(Consumer<MatrixEntry> func) {
    for (Map.Entry<MatrixCoordinate, Double> entry : entry_map_.entrySet()) {
      func.accept(new MatrixEntry(entry.getKey(), entry.getValue()));
    } 
  }

  @Override
  public Matrix Transpose() {
    Stream<Map.Entry<MatrixCoordinate, Double>> new_stream = entry_map_.entrySet().stream().map(entry -> {
      MatrixCoordinate coord = entry.getKey();
      return Map.entry(new MatrixCoordinate(coord.Col(), coord.Row()), entry.getValue());
    });
    Map<MatrixCoordinate, Double> entry_map =  
      new_stream.collect(Collectors.toMap(Map.Entry<MatrixCoordinate, Double>::getKey, 
                                          Map.Entry<MatrixCoordinate, Double>::getValue));
    return new COOMatrix(entry_map, Cols(), Rows());
  }

  @Override
  public int NumActiveEntries() {
    return entry_map_.size();
  }

  @Override 
  public boolean equals(Object o) {
    if (!(o instanceof COOMatrix)) {
      return false;
    }

    COOMatrix other = (COOMatrix)o;
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

  COOMatrix(Map<MatrixCoordinate, Double> map, int rows, int cols) {
    entry_map_ = map;
    rows_ = rows;
    cols_ = cols;
  }

  private Map<MatrixCoordinate, Double> entry_map_;
  private int rows_;
  private int cols_;
}