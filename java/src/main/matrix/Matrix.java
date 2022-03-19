// immutable matrix
interface Matrix {
  int Rows();
  int Cols();
  double ValueAt(int x, int y);
  Matrix Multiply(Matrix other);
  Matrix Multiply(Vector other);
  Matrix Add(Matrix other);
}