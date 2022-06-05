package linalg.algorithms;

import static linalg.algorithms.TestingUtils.QRDecomposeDoubleArray;
import org.junit.jupiter.api.Test;

class GivensRotationTest {
  GivensRotation givens_rotation = new GivensRotation();

  @Test
  void DecomposeNonSquareMatrix() {
    double[][] arr = {
      { 1.0, -1.0, 4.0 },
      { 1.0, 4.0, -2.0 },
      { 1.0, 4.0, 2.0 },
      { 1.0, -1.0, 0.0 }
    };

    QRDecomposeDoubleArray(arr, givens_rotation, 1e-10);
  }

  @Test
  void DecomposeIdentityMatrix() {
    double[][] arr = {
      { 1.0, 0.0, 0.0 },
      { 0.0, 1.0, 0.0 },
      { 0.0, 0.0, 1.0 }
    };

    QRDecomposeDoubleArray(arr, givens_rotation, 1e-10);
  }

  @Test
  void DecomposeZeroMatrix() {
    double[][] arr = {
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 }
    };

    QRDecomposeDoubleArray(arr, givens_rotation, 1e-10);
  }

  @Test
  void DecomposeVector() {
    double[][] arr = {
      { 1.0, 2.0, 3.0 }
    };

    QRDecomposeDoubleArray(arr, givens_rotation, 1e-10);
  }

  @Test
  void DecomposeWikipediaExample() {
    double[][] arr = {
      { 6.0, 5.0, 0.0 },
      { 5.0, 1.0, 4.0 },
      { 0.0, 4.0, 3.0 }
    };

    QRDecomposeDoubleArray(arr, givens_rotation, 1e-10);
  }
}