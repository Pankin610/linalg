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
}