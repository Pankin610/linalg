package linalg.algorithms;

import static org.junit.jupiter.api.Assertions.*;
import static linalg.algorithms.TestingUtils.CheckUpperTriangularDelta;
import static linalg.algorithms.TestingUtils.CompareMatrixWithArrayDelta;
import static linalg.algorithms.TestingUtils.CompareMatrixWithMatrixDelta;
import static linalg.matrix.MatrixFactory.IdentityMatrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import linalg.matrix.MatrixFactory;

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

    DecomposeDoubleArray(arr);
  }

  @Test
  void DecomposeIdentityMatrix() {
    double[][] arr = {
      { 1.0, 0.0, 0.0 },
      { 0.0, 1.0, 0.0 },
      { 0.0, 0.0, 1.0 }
    };

    DecomposeDoubleArray(arr);
  }

  @Test
  void DecomposeZeroMatrix() {
    double[][] arr = {
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 }
    };

    DecomposeDoubleArray(arr);
  }

  @Test
  void DecomposeVector() {
    double[][] arr = {
      { 1.0, 2.0, 3.0 }
    };

    DecomposeDoubleArray(arr);
  }

  @Test
  void DecomposeWikipediaExample() {
    double[][] arr = {
      { 6.0, 5.0, 0.0 },
      { 5.0, 1.0, 4.0 },
      { 0.0, 4.0, 3.0 }
    };

    DecomposeDoubleArray(arr);
  }
  
  void DecomposeDoubleArray(double[][] arr) {
    DenseMatrixBuilder dmb_a = new DenseMatrixBuilder(arr.length, arr[0].length);
    for (int i = 0; i < arr.length; i++)
      for (int j = 0; j < arr[0].length; j++)
        dmb_a.SetValue(i, j, arr[i][j]);

    givens_rotation.Decompose(dmb_a.BuildMatrix());

    Matrix Q = givens_rotation.GetQ();
    Matrix R = givens_rotation.GetR();

    Matrix id = MatrixFactory.DenseMultiply(Q, Q.Transpose());
    Matrix res = MatrixFactory.DenseMultiply(Q, R);

    Assertions.assertEquals(id.Rows(), id.Cols());
    Assertions.assertEquals(arr.length, res.Rows());
    Assertions.assertEquals(arr[0].length, res.Cols());

    CompareMatrixWithMatrixDelta(IdentityMatrix(id.Cols()), id, 1e-10);
    CheckUpperTriangularDelta(R, 1e-10);
    CompareMatrixWithArrayDelta(arr, res, 1e-10);
  }
}