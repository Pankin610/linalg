package linalg.matrix;

import linalg.matrix.MatrixCoordinate;
import linalg.matrix.COOMatrix;
import linalg.matrix.MatrixBuilder;

import java.util.Map;
import java.util.HashMap;

public class COOMatrixBuilder implements MatrixBuilder {
  public COOMatrixBuilder(int rows, int cols) {
    if (rows <= 0 || cols <= 0) {
      throw new IllegalArgumentException("Wrong matrix dimensions");
    }
    entry_map_ = new HashMap<>();
    rows_ = rows;
    cols_ = cols;
  }

  // TODO extra methods and error-pruning
  @Override
  public void SetValue(int row, int col, double value) {
    entry_map_.put(new MatrixCoordinate(row, col), value);
  }

  @Override
  public double GetValue(int row, int col) {
    return entry_map_.getOrDefault(new MatrixCoordinate(row, col), 0.);
  }

  @Override
  public Matrix BuildMatrix() {
    return new COOMatrix(entry_map_, rows_, cols_);
  }

  public COOMatrixBuilder(double mat[][]) {
    rows_ = mat.length;
    cols_ = mat[0].length;
    entry_map_ = new HashMap<>();
    for (int i = 0; i < rows_; i++) {
      for (int j = 0; j < cols_; j++) {
        if (Math.abs(mat[i][j]) < 1e-9) {
          continue;
        }
        SetValue(i, j, mat[i][j]);
      }
    }
  }

  Map<MatrixCoordinate, Double> entry_map_;
  int rows_; 
  int cols_;
};