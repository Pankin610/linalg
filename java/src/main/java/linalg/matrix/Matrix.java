package linalg.matrix;
import linalg.vector.Vector;

// immutable matrix
public interface Matrix {
  int Rows();
  int Cols();
  double ValueAt(int x, int y);
  Matrix Multiply(Matrix other);
  Matrix Multiply(Vector other);
  Matrix Add(Matrix other);
}