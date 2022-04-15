package linalg.matrix;

import linalg.vector.Vector;
import linalg.matrix.MatrixEntry;

import java.util.function.Consumer;

// immutable matrix
public interface Matrix {
  int Rows();
  int Cols();
  double ValueAt(int x, int y);
  Matrix Multiply(Matrix other);
  Vector Multiply(Vector other);
  Matrix Add(Matrix other);
  void ForEachEntry(Consumer<MatrixEntry> func);
}