package linalg.algorithms;

import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GramSchmidtTest {
  GramSchmidt gram_schmidt = new GramSchmidt();

  @Test
  void Decompose() {
    double[][] arr_a = {
      { 1.4, 1.7, 2.9 },
      { 0.7, 1.0, 3.0 },
      { 1.8, 4.3, 2.0 }
    };

    double[][] arr_q = {
      { 0.587, -0.716, -0.378 },
      { 0.293, -0.246, 0.924 },
      { 0.755, 0.653, -0.0655 }
    };

    double[][] arr_r = {
      { 2.39, 4.54, 4.09 },
      { 0, 1.34, -1.51 },
      { 0, 0, 1.54 }
    };

    DenseMatrixBuilder dmb_a = new DenseMatrixBuilder(3, 3);
    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        dmb_a.SetValue(i, j, arr_a[i][j]);

    gram_schmidt.Decompose(dmb_a.BuildMatrix());

    Matrix Q = gram_schmidt.GetQ();
    Matrix R = gram_schmidt.GetR();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Assertions.assertEquals(arr_q[i][j], Q.ValueAt(i, j), 0.01);
        Assertions.assertEquals(arr_r[i][j], R.ValueAt(i, j), 0.01);
      }
    }
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

    gram_schmidt.Decompose(dmb.BuildMatrix());

    Matrix Q = gram_schmidt.GetQ();
    Matrix R = gram_schmidt.GetR();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Assertions.assertEquals(arr[i][j], Q.ValueAt(i, j), 0.01);
        Assertions.assertEquals(arr[i][j], R.ValueAt(i, j), 0.01);
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

    DenseMatrixBuilder dmb = new DenseMatrixBuilder(3, 3);
    for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
        dmb.SetValue(i, j, arr[i][j]);

    gram_schmidt.Decompose(dmb.BuildMatrix());

    Matrix Q = gram_schmidt.GetQ();
    Matrix R = gram_schmidt.GetR();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Assertions.assertEquals(0.0, Q.ValueAt(i, j));
        Assertions.assertEquals(0.0, R.ValueAt(i, j));
      }
    }
  }

  @Test
  void DecomposeVector() {
    double[][] arr = {
      { 1.0, 2.0, 3.0 }
    };

    double[][] arr_q = {
      { 1.0, 0.0, 0.0 }
    };

    double[][] arr_r = {
      { 1.0, 2.0, 3.0 },
      { 0.0, 0.0, 0.0 },
      { 0.0, 0.0, 0.0 }
    };

    DenseMatrixBuilder dmb = new DenseMatrixBuilder(1, 3);
    for (int i = 0; i < 3; i++)
        dmb.SetValue(0, i, arr[0][i]);

    gram_schmidt.Decompose(dmb.BuildMatrix());

    Matrix Q = gram_schmidt.GetQ();
    Matrix R = gram_schmidt.GetR();

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