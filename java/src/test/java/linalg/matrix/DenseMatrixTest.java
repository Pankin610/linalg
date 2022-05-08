package linalg.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import linalg.random.RandomMatrices;

public class DenseMatrixTest {

  @Test
  public void NumberOfRowsAndExceptionHandlingWhileCreatingDenseMatrixWithIntegers() {
    String expectedMessage = "Invalid number of rows or columns";

    for (int i = -10; i <= 10; i++) {
      for (int j = -10; j <= 10; j++) {
        try {
          new DenseMatrix(i, j);
        } catch (IllegalArgumentException e) {
          Assertions.assertEquals(expectedMessage, e.getMessage());
          continue;
        }
        if (i <= 0 || j <= 0)
          Assertions.fail();
        DenseMatrix dm = new DenseMatrix(i, j);
        Assertions.assertEquals(dm.rows_, i);
      }
    }
  }

  @Test
  public void NumberOfColumnsAndExceptionHandlingWhileCreatingDenseMatrixWithIntegers() {
    String expectedMessage = "Invalid number of rows or columns";

    for (int i = -10; i <= 10; i++) {
      for (int j = -10; j <= 10; j++) {
        try {
          new DenseMatrix(i, j);
        } catch (IllegalArgumentException e) {
          Assertions.assertEquals(expectedMessage, e.getMessage());
          continue;
        }
        if (i <= 0 || j <= 0)
          Assertions.fail();
        DenseMatrix dm = new DenseMatrix(i, j);
        Assertions.assertEquals(dm.cols_, j);
      }
    }
  }

  @Test
  public void NumberOfRowsWhileCreatingDenseMatrixWithArray() {
    for (int i = 1; i <= 10; i++) {
      for (int j = 1; j <= 10; j++) {
        double[][] array_of_doubles = new double[i][j];
        DenseMatrix dm = new DenseMatrix(array_of_doubles);
        Assertions.assertEquals(dm.rows_, i);
      }
    }
  }

  @Test
  public void NumberOfColumnsWhileCreatingDenseMatrixWithArray() {
    for (int i = 1; i <= 10; i++) {
      for (int j = 1; j <= 10; j++) {
        double[][] array_of_doubles = new double[i][j];
        DenseMatrix dm = new DenseMatrix(array_of_doubles);
        Assertions.assertEquals(dm.cols_, j);
      }
    }
  }

  @Test
  public void ValueAtDiagonalForSquareDenseMatrix() {
    for (int i = 1; i <= 10; i++) {
      double[][] array_of_doubles = new double[i][i];
      for (int j = 0; j < i; j++)
        array_of_doubles[j][j] = j;
      DenseMatrix dm = new DenseMatrix(array_of_doubles);
      for (int j = 0; j < i; j++)
        Assertions.assertEquals(j, dm.ValueAt(j, j), 0);
    }
  }

  @Test
  public void ValueAtForDifferentSizedMatrix() {
    for (int i = 1; i <= 10; i++) {
      for (int j = 1; j <= 10; j++) {
        double[][] array_of_doubles = new double[i][j];
        for (int k = 0; k < i; k++)
          for (int l = 0; l < j; l++)
            array_of_doubles[k][l] = k * l;
        DenseMatrix dm = new DenseMatrix(array_of_doubles);
        for (int k = 0; k < i; k++)
          for (int l = 0; l < j; l++)
            Assertions.assertEquals(k * l, dm.ValueAt(k, l), 0);
      }
    }
  }

  @Test
  public void ForEachEntryForSmallMatrixSimpleValueCheck() {
    double[][] arr = new double[10][10];
    for (int i = 0; i < 10; i++)
      for (int j = 0; j < 10; j++)
        arr[i][j] = i * i + j;
    DenseMatrix dm = new DenseMatrix(arr);
    dm.ForEachEntry(entry -> Assertions.assertEquals(entry.Row() * entry.Row() + entry.Col(), entry.Value(), 0));
  }

  @Test
  void TransposeWorksCorrectly() {
    final int rows = 100;
    final int cols = 200;
    DenseMatrix mat = (DenseMatrix)RandomMatrices.RandomDenseMatrix(rows, cols);
    DenseMatrix transposed = (DenseMatrix)mat.Transpose();
    Assertions.assertEquals(mat.Rows(), transposed.Cols());
    Assertions.assertEquals(mat.Cols(), transposed.Rows());
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        Assertions.assertEquals(mat.ValueAt(i, j), transposed.ValueAt(j, i));
      }
    }
  }
} 