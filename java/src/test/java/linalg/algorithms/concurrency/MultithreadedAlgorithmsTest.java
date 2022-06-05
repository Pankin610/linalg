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
      Assertions.assertEquals(res, expected_res);
    }
    catch(Exception e) {
      Assertions.fail();
    }
  }

  @Test
  public void BasicSparseMultiplication() {
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

    Matrix dm1 = new COOMatrixBuilder(arr1).BuildMatrix();
    Matrix dm2 = new COOMatrixBuilder(arr2).BuildMatrix();
    Matrix expected_res = new COOMatrixBuilder(arr3).BuildMatrix();
    try {
      SparseMatrix res = MultithreadedAlgorithms.SparseMatrixProduct(dm1, dm2);
      Assertions.assertEquals(res, expected_res);
    }
    catch(Exception e) {
      Assertions.fail();
    }
  }

  @Test
  public void HarderSparseMultiplication() {
    double[][] arr1 = {
      {1, 2, 3},
      {5, 0, 0},
      {1, 0, 5},
      {0, 4, 5},
      {1, 5, 0}
    };

    double[][] arr2 = {
      {0, 0, 0, 0, 0},
      {5, 3, 1, 0, 1},
      {6, 0, 1, 0, 3},
    };

    double[][] arr3 = {
      {28, 6, 5, 0, 11},
      {0, 0, 0, 0, 0},
      {30, 0, 5, 0, 15},
      {50, 12, 9, 0, 19},
      {25, 15, 5, 0, 5}
    };
    
    Matrix dm1 = new COOMatrixBuilder(arr1).BuildMatrix();
    Matrix dm2 = new COOMatrixBuilder(arr2).BuildMatrix();
    Matrix expected_res = new COOMatrixBuilder(arr3).BuildMatrix();
    try {
      SparseMatrix res = MultithreadedAlgorithms.SparseMatrixProduct(dm1, dm2);
      Assertions.assertEquals(res, expected_res);
    }
    catch(Exception e) {
      Assertions.fail();
    }
  }
}