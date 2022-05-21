package linalg.algorithms;

import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HouseholderTest {
  Householder householder = new Householder();
  
  @Test
  void Decompose() {
    double[][] arr_a = {
      { 1.0, -1.0, 4.0 },
      { 1.0, 4.0, -2.0 },
      { 1.0, 4.0, 2.0 },
      { 1.0, -1.0, 0.0 }
    };

    double[][] arr_q = {
      { -0.5, 0.5, -0.5, -0.5 },
      { -0.5, -0.5, 0.5, -0.5 },
      { -0.5, -0.5, -0.5, 0.5 },
      { -0.5, 0.5, 0.5, 0.5 }
    };

    double[][] arr_r = {
      { -2.0, -3.0, -2.0 },
      { 0.0, -5.0, 2.0 },
      { 0.0, 0.0, -4.0 },
      { 0.0, 0.0, 0.0 }
    };

    DenseMatrixBuilder dmb_a = new DenseMatrixBuilder(4, 3);
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 3; j++)
        dmb_a.SetValue(i, j, arr_a[i][j]);

    householder.Decompose(dmb_a.BuildMatrix());

    Matrix Q = householder.GetQ();
    Matrix R = householder.GetR();

    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        Assertions.assertEquals(arr_q[i][j], Q.ValueAt(i, j), 0.01);

    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 3; j++)
        Assertions.assertEquals(arr_r[i][j], R.ValueAt(i, j), 0.01);
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

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Assertions.assertEquals(-arr[i][j], Q.ValueAt(i, j), 0.01);
        Assertions.assertEquals(-arr[i][j], R.ValueAt(i, j), 0.01);
      }
    }
  }

  @Test
  void DecomposeIZeroMatrix() {
    double[][] arr = {
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 }
    };

    double[][] arr_id = {
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

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Assertions.assertEquals(arr_id[i][j], Q.ValueAt(i, j));
        Assertions.assertEquals(arr[i][j], R.ValueAt(i, j));
      }
    }
  }

  @Test
  void DecomposeVector() {
    double[][] arr = {
      { 1.0, 2.0, 3.0 }
    };

    double[][] arr_q = {
      { -1.0 }
    };

    double[][] arr_r = {
      { -1.0, -2.0, -3.0 },
    };

    DenseMatrixBuilder dmb = new DenseMatrixBuilder(1, 3);
    for (int i = 0; i < 3; i++)
      dmb.SetValue(0, i, arr[0][i]);

    householder.Decompose(dmb.BuildMatrix());

    Matrix Q = householder.GetQ();
    Matrix R = householder.GetR();

    Assertions.assertEquals(arr_r.length, R.Rows());
    Assertions.assertEquals(arr_r[0].length, R.Cols());

    Assertions.assertEquals(arr_q.length, Q.Rows());
    Assertions.assertEquals(arr_q[0].length, Q.Cols());

    for (int i = 0; i < Q.Rows(); i++)
      for (int j = 0; j < Q.Cols(); j++)
        Assertions.assertEquals(arr_q[i][j], Q.ValueAt(i, j));

    for (int i = 0; i < R.Rows(); i++)
      for (int j = 0; j < R.Cols(); j++)
        Assertions.assertEquals(arr_r[i][j], R.ValueAt(i, j));
  }
}