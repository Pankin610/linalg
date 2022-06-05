package linalg.algorithms;

import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import linalg.matrix.MatrixFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static linalg.algorithms.TestingUtils.*;
import static linalg.matrix.MatrixFactory.IdentityMatrix;

class HouseholderTest {
  Householder householder = new Householder();

  @Test
  void Decompose() {
    double[][] arr = {
      { 1.0, -1.0, 4.0 },
      { 1.0, 4.0, -2.0 },
      { 1.0, 4.0, 2.0 },
      { 1.0, -1.0, 0.0 }
    };

    QRDecomposeDoubleArray(arr, householder, 1e-10);
  }

  @Test
  void DecomposeIdentityMatrix() {
    double[][] arr = {
      { 1.0, 0.0, 0.0 },
      { 0.0, 1.0, 0.0 },
      { 0.0, 0.0, 1.0 }
    };

    QRDecomposeDoubleArray(arr, householder, 1e-10);
  }

  @Test
  void DecomposeIZeroMatrix() {
    double[][] arr = {
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 }
    };

    QRDecomposeDoubleArray(arr, householder, 1e-10);
  }

  @Test
  void DecomposeVector() {
    double[][] arr = {
      { 1.0, 2.0, 3.0 }
    };

    QRDecomposeDoubleArray(arr, householder, 1e-10);
  }
}