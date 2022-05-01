package linalg.algorithms;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import linalg.matrix.DenseMatrixBuilder;

class LUDecompositionTest {

  @Test
  void LUDecompositionConstructorCorrectInput() {
    double[][] arr = {
      {1, 2, 3, 4, 5},
      {5, 4, 3, 2, 1},
      {1, 3, 5, 2, 3},
      {2, 4, 5, 3, 1},
      {1, 5, 2, 4, 3}
    };
    DenseMatrixBuilder dmb = new DenseMatrixBuilder(5, 5);
    for (int i = 0; i < 5; i++)
      for (int j = 0; j < 5; j++)
        dmb.SetValue(i, j, arr[i][j]);

    LUDecomposition lu_decomposition = new LUDecomposition(dmb.BuildMatrix());

    Assertions.assertNull(lu_decomposition.L);
    Assertions.assertNull(lu_decomposition.U);
    Assertions.assertNotNull(lu_decomposition.Mat);

    Assertions.assertEquals(5, lu_decomposition.Mat.Rows());
    Assertions.assertEquals(5, lu_decomposition.Mat.Cols());

    for (int i = 0; i < 5; i++)
      for (int j = 0; j < 5; j++)
        Assertions.assertEquals(arr[i][j], lu_decomposition.Mat.ValueAt(i, j));
  }

  @Test
  void LUDecompositionConstructorIncorrectInput() {
    double[][] arr = {
      {1, 2, 3, 4, 5},
      {5, 4, 3, 2, 1},
      {1, 3, 5, 2, 3},
    };
    DenseMatrixBuilder dmb = new DenseMatrixBuilder(5, 5);
    for (int i = 0; i < 5; i++)
      for (int j = 0; j < 5; j++)
        dmb.SetValue(i, j, arr[i][j]);

    try {
      new LUDecomposition(dmb.BuildMatrix());
    } catch (IllegalArgumentException e) {
      return;
    }

    Assertions.fail();
  }

  @Test
  void ConstructionLUSmallCase() {
    double[][] arr = {
      {2, 3, 1, 5},
      {6, 13, 5, 19},
      {2, 19, 10, 23},
      {4, 10, 11, 31}
    };
    DenseMatrixBuilder dmb = new DenseMatrixBuilder(5, 5);
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        dmb.SetValue(i, j, arr[i][j]);

    LUDecomposition lu_decomposition = new LUDecomposition(dmb.BuildMatrix());

    /*
     * Preparation of test above
     */

    double[][] L = {
      {1, 0, 0, 0},
      {3, 1, 0, 0},
      {1, 4, 1, 0},
      {2, 1, 7, 1}
    };

    double[][] U = {
      {2, 3, 1, 5},
      {0, 4, 2, 4},
      {0, 0, 1, 2},
      {0, 0, 0, 3}
    };

    try {
      lu_decomposition.ConstructionLU();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Assertions.fail();
      return;
    }

    for (int i = 0; i < 5; i++)
      for (int j = 0; j < 5; j++)
        Assertions.assertEquals(L[i][j], lu_decomposition.L.ValueAt(i, j));

    for (int i = 0; i < 5; i++)
      for (int j = 0; j < 5; j++)
        Assertions.assertEquals(U[i][j], lu_decomposition.U.ValueAt(i, j));
  }

  @Test
  void Determinant() {
    double[][] arr = {
      {2, 3, 1, 5},
      {6, 13, 5, 19},
      {2, 19, 10, 23},
      {4, 10, 11, 31}
    };
    DenseMatrixBuilder dmb = new DenseMatrixBuilder(5, 5);
    for (int i = 0; i < 4; i++)
      for (int j = 0; j < 4; j++)
        dmb.SetValue(i, j, arr[i][j]);

    LUDecomposition lu_decomposition = new LUDecomposition(dmb.BuildMatrix());

    Assertions.assertEquals(24, lu_decomposition.Determinant());
  }
}