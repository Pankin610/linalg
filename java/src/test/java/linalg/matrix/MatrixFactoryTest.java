package linalg.matrix;

import static linalg.matrix.MatrixFactory.Add;
import static linalg.matrix.MatrixFactory.DenseMultiply;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

class MatrixFactoryTest {
  final int test_size = 10;
  @Test
  void AddForTwoDenseMatrixWithExceptionHandlingForIncorrectDimensions() {
    String expected_message = "Matrix dimensions don't add up.";
    for (int i = 1; i <= test_size; i++) {
      for (int j = 1; j <= test_size; j++) {
        for (int k = 1; k <= test_size; k++) {
          for (int l = 1; l <= test_size; l++) {
            DenseMatrix dm1 = (DenseMatrix) new DenseMatrixBuilder(i, j).BuildMatrix();
            DenseMatrix dm2 = (DenseMatrix) new DenseMatrixBuilder(k, l).BuildMatrix();
            try {
              Add(dm1, dm2);
            } catch (IllegalArgumentException e) {
              if (dm1.Rows() == dm2.Rows() && dm1.Cols() == dm2.Cols())
                Assertions.fail();
              Assertions.assertEquals(expected_message, e.getMessage());
              continue;
            }
            if (dm1.Rows() != dm2.Rows() || dm1.Cols() != dm2.Cols())
              Assertions.fail();

            DenseMatrix sum = (DenseMatrix) new DenseMatrixBuilder(i, j).BuildMatrix();
            for (int row = 0; row < i; row++) {
              for (int col = 0; col < j; col++) {
                dm1.mat_[row][col] = new Random().nextInt();
                dm2.mat_[row][col] = new Random().nextInt();
                sum.mat_[row][col] = dm1.mat_[row][col] + dm2.mat_[row][col];
              }
            }
            DenseMatrix res = (DenseMatrix) Add(dm1, dm2);

            for (int row = 0; row < i; row++) {
              for (int col = 0; col < j; col++) {
                Assertions.assertEquals(sum.ValueAt(row, col), res.ValueAt(row, col));
              }
            }
          }
        }
      }
    }
  }

  @Test
  void DenseMultiplyWithCorrectDimensionsSquareSize() {
    double[][] arr1 = {
      {1, 2, 3, 4, 5},
      {5, 4, 3, 2, 1},
      {1, 3, 5, 2, 3},
      {2, 4, 5, 3, 1},
      {1, 5, 2, 4, 3}
    };

    double[][] arr2 = {
      {4, 2, 1, 4, 5},
      {5, 3, 1, 2, 1},
      {6, 1, 1, 2, 3},
      {7, 2, 1, 3, 1},
      {8, 3, 1, 4, 3}
    };

    double[][] arr3 = {
      {100, 34, 15, 46, 35},
      {80, 32, 15, 44, 43},
      {87, 29, 14, 38, 34},
      {87, 30, 15, 39, 35},
      {93, 36, 15, 42, 29}
    };

    DenseMatrix dm1 = new DenseMatrix(arr1);
    DenseMatrix dm2 = new DenseMatrix(arr2);
    DenseMatrix expected = new DenseMatrix(arr3);

    DenseMatrix res = DenseMultiply(dm1, dm2);

    for (int i = 0; i < expected.Rows(); i++)
      for (int j = 0; j < expected.Cols(); j++)
        Assertions.assertEquals(expected.mat_[i][j], res.mat_[i][j]);
  }

  @Test
  void DenseMultiplyWithCorrectDimensionsNonSquareSize() {
    double[][] arr1 = {
      {1, 2, 3},
      {5, 4, 3},
      {1, 3, 5},
      {2, 4, 5},
      {1, 5, 2}
    };

    double[][] arr2 = {
      {4, 2, 1, 4, 5},
      {5, 3, 1, 2, 1},
      {6, 1, 1, 2, 3},
    };

    double[][] arr3 = {
      {32, 11, 6, 14, 16},
      {58, 25, 12, 34, 38},
      {49, 16, 9, 20, 23},
      {58, 21, 11, 26, 29},
      {41, 19, 8, 18, 16}
    };

    DenseMatrix dm1 = new DenseMatrix(arr1);
    DenseMatrix dm2 = new DenseMatrix(arr2);
    DenseMatrix expected = new DenseMatrix(arr3);

    DenseMatrix res = DenseMultiply(dm1, dm2);

    for (int i = 0; i < expected.Rows(); i++)
      for (int j = 0; j < expected.Cols(); j++)
        Assertions.assertEquals(expected.mat_[i][j], res.mat_[i][j]);
  }

  @Test
  void DenseMultiplyIncorrectDimensionsExceptionHandling() {
    String expected = "Matrix dimensions don't add up.";
    double[][] arr1 = {
      {1, 2, 3},
      {5, 4, 3},
      {1, 3, 5},
      {2, 4, 5},
      {1, 5, 2}
    };

    double[][] arr2 = {
      {4, 2, 1, 4, 5},
      {5, 3, 1, 2, 1},
    };

    DenseMatrix dm1 = new DenseMatrix(arr1);
    DenseMatrix dm2 = new DenseMatrix(arr2);

    try {
      DenseMultiply(dm1, dm2);
    } catch (IllegalArgumentException e) {
      Assertions.assertEquals(expected, e.getMessage());
    }

    try {
      DenseMultiply(dm2, dm1);
    } catch (IllegalArgumentException e) {
      Assertions.assertEquals(expected, e.getMessage());
    }
  }

  @Test
  void sparseMultiply() {
    // TODO: SparseMultiplyTests
  }
}