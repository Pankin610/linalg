package linalg.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DenseMatrixBuilderTest {

  @Test
  void SetValueForSmallMatrixWithExceptionHandlingWhileCreatingArrayWithIntegers() {
    String expected_message = "Wrong matrix dimensions";

    for (int i = -10; i <= 10; i++) {
      for (int j = -10; j <= 10; j++) {
        try {
          new DenseMatrixBuilder(i, j);
        } catch (IllegalArgumentException e) {
          if (i > 0 && j > 0)
            Assertions.fail();
          Assertions.assertEquals(expected_message, e.getMessage());
          continue;
        }
        if (i <= 0 || j <= 0)
          Assertions.fail();
        DenseMatrixBuilder dmb = new DenseMatrixBuilder(i, j);
        for (int k = 0; k < i; k++)
          for (int l = 0; l < j; l++)
            dmb.SetValue(k, l, k * 100 + l);
        for (int k = 0; k < i; k++)
          for (int l = 0; l < j; l++)
            Assertions.assertEquals(dmb.mat_array_[k][l], k * 100 + l, 0);
      }
    }
  }

  @Test
  void GetValueForSmallMatrixWithExceptionHandlingWhileCreatingArrayWithIntegers() {
    String expected_message = "Wrong matrix dimensions";

    for (int i = -10; i <= 10; i++) {
      for (int j = -10; j <= 10; j++) {
        try {
          new DenseMatrixBuilder(i, j);
        } catch (IllegalArgumentException e) {
          if (i > 0 && j > 0)
            Assertions.fail();
          Assertions.assertEquals(expected_message, e.getMessage());
          continue;
        }
        if (i <= 0 || j <= 0)
          Assertions.fail();
        DenseMatrixBuilder dmb = new DenseMatrixBuilder(i, j);
        for (int k = 0; k < i; k++)
          for (int l = 0; l < j; l++)
            dmb.mat_array_[k][l] = k * 100 + l;
        for (int k = 0; k < i; k++)
          for (int l = 0; l < j; l++)
            Assertions.assertEquals(dmb.GetValue(k, l), k * 100 + l, 0);
      }
    }
  }

  @Test
  void BuildMatrixComparisonWithDenseMatrixConstructorRowsColsAndDefaultValue() {
    for (int i = 1; i <= 10; i++) {
      for (int j = 1; j <= 10; j++) {
        DenseMatrix dm1 = new DenseMatrix(i, j);
        DenseMatrix dm2 = (DenseMatrix) new DenseMatrixBuilder(i, j).BuildMatrix();
        Assertions.assertEquals(dm1.Rows(), dm2.Rows());
        Assertions.assertEquals(dm2.Cols(), dm2.Cols());
        for (int k = 0; k < i; k++)
          for (int l = 0; l < j; l++)
            Assertions.assertEquals(dm1.ValueAt(k, l), dm2.ValueAt(k, l));
      }
    }
  }
}