package linalg.algorithms;

import linalg.matrix.Matrix;

import java.util.concurrent.ExecutionException;

public interface QRDecomposition {
  Matrix GetQ();
  Matrix GetR();
  void Decompose(Matrix matrix) throws InterruptedException, ExecutionException;
}
