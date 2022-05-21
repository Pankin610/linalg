package linalg.algorithms.eigen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import linalg.algorithms.eigen.EigenUtils;
import linalg.matrix.*;

class EigenUtilsTest {
  @Test
  public void BasicDiagonalMatrix() {
    double[][] arr = {
      { 2.0, 0.0, 0.0 },
      { 0.0, 2.0, 0.0 },
      { 0.0, 0.0, 2.0 }
    };
    DenseMatrixBuilder builder = new DenseMatrixBuilder(3, 3);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        builder.SetValue(i, j, arr[i][j]);
      }
    }

    Assertions.assertEquals(
      EigenUtils.GetGreatestEigenValue(builder.BuildMatrix(), 1, 1e-9), 2.0);
  }

  @Test
  public void DiagonalMatrixWithDifferentVals() {
    double[][] arr = {
      { 2.0, 0.0, 0.0 },
      { 0.0, 3.0, 0.0 },
      { 0.0, 0.0, 1.0 }
    };
    DenseMatrixBuilder builder = new DenseMatrixBuilder(3, 3);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        builder.SetValue(i, j, arr[i][j]);
      }
    }

    Assertions.assertEquals(
      EigenUtils.GetGreatestEigenValue(builder.BuildMatrix(), 100, 1e-9), 3.0);
  }
}