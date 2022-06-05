package linalg.algorithms.concurrency;

import linalg.algorithms.concurrency.MultithreadedAlgorithms;
import linalg.matrix.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MultithreadedAlgorithmsTest {
  @Test
  public void BasicDenseMultiplication() {
    double[][] arr1 = {
      {2, 0, 0},
      {0, 2, 0},
      {0, 0, 2}
    };
    double[][] arr2 = {
      {1, 2, 3},
      {7, 6, 5},
      {8, 9, 10}
    };
    double[][] arr3 = {
      {2, 4, 6},
      {14, 12, 10},
      {16, 18, 20}
    };
    Matrix dm1 = new DenseMatrixBuilder(arr1).BuildMatrix();
    Matrix dm2 = new DenseMatrixBuilder(arr2).BuildMatrix();
    Matrix expected_res = new DenseMatrixBuilder(arr3).BuildMatrix();

    try {
      DenseMatrix res = MultithreadedAlgorithms.DenseMatrixProduct(dm1, dm2);
      System.out.println(res);
      Assertions.assertEquals(res, expected_res);
    }
    catch(Exception e) {
      Assertions.fail();
    }
  }

  @Test
  public void HarderDenseMultiplication() {
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
    
    Matrix dm1 = new DenseMatrixBuilder(arr1).BuildMatrix();
    Matrix dm2 = new DenseMatrixBuilder(arr2).BuildMatrix();
    Matrix expected_res = new DenseMatrixBuilder(arr3).BuildMatrix();

    try {
      DenseMatrix res = MultithreadedAlgorithms.DenseMatrixProduct(dm1, dm2);
      System.out.println(res);
      Assertions.assertEquals(res, expected_res);
    }
    catch(Exception e) {
      Assertions.fail();
    }
  }
}