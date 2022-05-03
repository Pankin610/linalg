package linalg.matrix;

import linalg.matrix.Matrix;
import linalg.matrix.SparseMatrix;
import linalg.matrix.MatrixCoordinate;
import linalg.matrix.MatrixEntry;

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

  COOMatrix(Map<MatrixCoordinate, Double> map, int rows, int cols) {
    entry_map_ = map;
    rows_ = rows;
    cols_ = cols;
  }

  private Map<MatrixCoordinate, Double> entry_map_;
  private int rows_;
  private int cols_;
}