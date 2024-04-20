package linalg.algorithms;

import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import linalg.matrix.MatrixFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static linalg.algorithms.TestingUtils.*;
import static linalg.matrix.MatrixFactory.IdentityMatrix;

class GramSchmidtTest {
  GramSchmidt gram_schmidt = new GramSchmidt();

  @Test
  void Decompose() throws InterruptedException, ExecutionException {
    double[][] arr = {
      { 1.0, -1.0, 4.0 },
      { 1.0, 4.0, -2.0 },
      { 1.0, 4.0, 2.0 },
      { 1.0, -1.0, 0.0 }
    };

    QRDecomposeDoubleArray(arr, gram_schmidt, 0.25);
  }

  @Test
  void DecomposeIdentityMatrix() throws InterruptedException, ExecutionException {
    double[][] arr = {
      { 1.0, 0.0, 0.0 },
      { 0.0, 1.0, 0.0 },
      { 0.0, 0.0, 1.0 }
    };

    QRDecomposeDoubleArray(arr, gram_schmidt, 1e-5);
  }

  @Test
  void DecomposeZeroMatrix() throws InterruptedException, ExecutionException {
    double[][] arr = {
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 }
    };

    QRDecomposeDoubleArray(arr, gram_schmidt, 1e-5);
  }

  @Test
  void DecomposeVector() throws InterruptedException, ExecutionException {
    double[][] arr = {
      { 1.0, 2.0, 3.0 }
    };

    QRDecomposeDoubleArray(arr, gram_schmidt, 1e-5);
  }
}