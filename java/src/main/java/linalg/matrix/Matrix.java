package linalg.matrix;

import linalg.vector.Vector;
import linalg.matrix.MatrixEntry;

import java.util.function.Consumer;

// immutable matrix
public interface Matrix {
  int Rows();
  int Cols();
  double ValueAt(int x, int y);
  void ForEachEntry(Consumer<MatrixEntry> func);
  Matrix Transpose();
  // Number of entries that should be consumed by the ForEachEntry function.
  int NumActiveEntries();
}