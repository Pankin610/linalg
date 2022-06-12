package linalg.algorithms;

import static linalg.algorithms.TestingUtils.QRDecomposeDoubleArray;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

class ConcurrentGivensRotationTest {
  ConcurrentGivensRotation concurrent_givens_rotation = new ConcurrentGivensRotation();

  @Test
  void DecomposeNonSquareMatrix() throws InterruptedException, ExecutionException {
    double[][] arr = {
      { 1.0, -1.0, 4.0 },
      { 1.0, 4.0, -2.0 },
      { 1.0, 4.0, 2.0 },
      { 1.0, -1.0, 0.0 }
    };

    QRDecomposeDoubleArray(arr, concurrent_givens_rotation, 1e-10);
  }

  @Test
  void DecomposeIdentityMatrix() throws InterruptedException, ExecutionException {
    double[][] arr = {
      { 1.0, 0.0, 0.0 },
      { 0.0, 1.0, 0.0 },
      { 0.0, 0.0, 1.0 }
    };

    QRDecomposeDoubleArray(arr, concurrent_givens_rotation, 1e-10);
  }

  @Test
  void DecomposeZeroMatrix() throws InterruptedException, ExecutionException {
    double[][] arr = {
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 }
    };

    QRDecomposeDoubleArray(arr, concurrent_givens_rotation, 1e-10);
  }

  @Test
  void DecomposeVector() throws InterruptedException, ExecutionException {
    double[][] arr = {
      { 1.0, 2.0, 3.0 }
    };

    QRDecomposeDoubleArray(arr, concurrent_givens_rotation, 1e-10);
  }

  @Test
  void DecomposeWikipediaExample() throws InterruptedException, ExecutionException {
    double[][] arr = {
      { 6.0, 5.0, 0.0 },
      { 5.0, 1.0, 4.0 },
      { 0.0, 4.0, 3.0 }
    };

    QRDecomposeDoubleArray(arr, concurrent_givens_rotation, 1e-10);
  }
}