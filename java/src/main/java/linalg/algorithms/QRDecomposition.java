package linalg.algorithms;

import linalg.matrix.Matrix;

public interface QRDecomposition {
  Matrix GetQ();
  Matrix GetR();
  void Decompose(Matrix matrix);
}
