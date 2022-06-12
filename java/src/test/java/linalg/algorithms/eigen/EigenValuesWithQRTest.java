package linalg.algorithms.eigen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import linalg.algorithms.eigen.EigenValuesWithQR;
import linalg.matrix.*;
import java.util.Collection;
import java.util.ArrayList;
import linalg.algorithms.GramSchmidt;
import static linalg.algorithms.TestingUtils.SameDoubleCollections;

import java.lang.IllegalArgumentException;
import java.util.concurrent.ExecutionException;

class EigenValuesWithQRTest {
  @Test
  public void BasicDiagonalMatrix() throws InterruptedException, ExecutionException {
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

    EigenValuesComputer eigen_computer = new EigenValuesWithQR(new GramSchmidt());
    Collection<Double> eigen_values = 
      eigen_computer.GetEigenValues(builder.BuildMatrix());
    ArrayList<Double> expected_eigen = new ArrayList<>();
    expected_eigen.add(2.0);
    expected_eigen.add(1.0);
    expected_eigen.add(3.0);
    Assertions.assertTrue(eigen_values.containsAll(expected_eigen));
  }

  @Test
  public void BasicMatrix() throws InterruptedException, ExecutionException {
    double[][] arr = {
      { -5.0, 2.0 },
      { -7.0, 4.0 }
    };
    DenseMatrixBuilder builder = new DenseMatrixBuilder(2, 2);
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        builder.SetValue(i, j, arr[i][j]);
      }
    }

    EigenValuesComputer eigen_computer = new EigenValuesWithQR(new GramSchmidt());
    Collection<Double> eigen_values = 
      eigen_computer.GetEigenValues(builder.BuildMatrix());
    ArrayList<Double> expected_eigen = new ArrayList<>();
    expected_eigen.add(2.0);
    expected_eigen.add(-3.0);
    Assertions.assertTrue(SameDoubleCollections(expected_eigen, eigen_values, 1e-9));
  }
}