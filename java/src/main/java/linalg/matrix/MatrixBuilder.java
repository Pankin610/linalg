package linalg.matrix;

import linalg.matrix.Matrix;

public interface MatrixBuilder {
  void SetValue(int x, int y, double value);
  double GetValue(int x, int y);
  Matrix BuildMatrix();
}