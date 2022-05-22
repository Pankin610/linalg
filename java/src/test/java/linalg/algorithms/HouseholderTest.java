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

    DenseMatrixBuilder dmb_a = new DenseMatrixBuilder(4, 3);
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 3; j++)
        dmb_a.SetValue(i, j, arr[i][j]);

    householder.Decompose(dmb_a.BuildMatrix());

    Matrix Q = householder.GetQ();
    Matrix R = householder.GetR();

    Matrix id = MatrixFactory.DenseMultiply(Q, Q.Transpose());
    Matrix res = MatrixFactory.DenseMultiply(Q, R);

    Assertions.assertEquals(id.Rows(), id.Cols());
    Assertions.assertEquals(arr.length, res.Rows());
    Assertions.assertEquals(arr[0].length, res.Cols());

    CompareMatrixWithMatrixDelta(IdentityMatrix(id.Cols()), id, 0.01);
    CheckUpperTriangularDelta(R, 0.01);
    CompareMatrixWithArrayDelta(arr, res, 0.01);
  }

  @Test
  void DecomposeIdentityMatrix() {
    double[][] arr = {
      { 1.0, 0.0, 0.0 },
      { 0.0, 1.0, 0.0 },
      { 0.0, 0.0, 1.0 }
    };

    DenseMatrixBuilder dmb = new DenseMatrixBuilder(3, 3);
    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        dmb.SetValue(i, j, arr[i][j]);

    householder.Decompose(dmb.BuildMatrix());
    Matrix Q = householder.GetQ();
    Matrix R = householder.GetR();

    Matrix id = MatrixFactory.DenseMultiply(Q, Q.Transpose());
    Matrix res = MatrixFactory.DenseMultiply(Q, R);

    Assertions.assertEquals(id.Rows(), id.Cols());
    Assertions.assertEquals(arr.length, res.Rows());
    Assertions.assertEquals(arr[0].length, res.Cols());

    CompareMatrixWithMatrixDelta(IdentityMatrix(id.Cols()), id, 0.01);
    CheckUpperTriangularDelta(R, 0.01);
    CompareMatrixWithArrayDelta(arr, res, 0.01);
  }

  @Test
  void DecomposeIZeroMatrix() {
    double[][] arr = {
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 }
    };

    DenseMatrixBuilder dmb = new DenseMatrixBuilder(3, 3);
    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        dmb.SetValue(i, j, arr[i][j]);

    householder.Decompose(dmb.BuildMatrix());

    Matrix Q = householder.GetQ();
    Matrix R = householder.GetR();

    Matrix id = MatrixFactory.DenseMultiply(Q, Q.Transpose());
    Matrix res = MatrixFactory.DenseMultiply(Q, R);

    Assertions.assertEquals(id.Rows(), id.Cols());
    Assertions.assertEquals(arr.length, res.Rows());
    Assertions.assertEquals(arr[0].length, res.Cols());

    CompareMatrixWithMatrixDelta(IdentityMatrix(id.Rows()), id, 0.01);
    CheckUpperTriangularDelta(R, 0.01);
    CompareMatrixWithArrayDelta(arr, res, 0.01);
  }

  @Test
  void DecomposeVector() {
    double[][] arr = {
      { 1.0, 2.0, 3.0 }
    };

    DenseMatrixBuilder dmb = new DenseMatrixBuilder(1, 3);
    for (int i = 0; i < 3; i++)
      dmb.SetValue(0, i, arr[0][i]);

    householder.Decompose(dmb.BuildMatrix());

    Matrix Q = householder.GetQ();
    Matrix R = householder.GetR();

    Matrix id = MatrixFactory.DenseMultiply(Q, Q.Transpose());
    Matrix res = MatrixFactory.DenseMultiply(Q, R);

    Assertions.assertEquals(id.Rows(), id.Cols());
    Assertions.assertEquals(arr.length, res.Rows());
    Assertions.assertEquals(arr[0].length, res.Cols());

    CompareMatrixWithMatrixDelta(IdentityMatrix(id.Rows()), id, 0.01);
    CheckUpperTriangularDelta(R, 0.01);
    CompareMatrixWithArrayDelta(arr, res, 0.01);
  }
}