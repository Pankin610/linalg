package linalg.algorithms.eigen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import linalg.algorithms.eigen.EigenValuesWithQR;
import linalg.matrix.*;
import java.util.Collection;
import java.util.ArrayList;
import linalg.algorithms.GramSchmidt;

import java.lang.IllegalArgumentException;

class EigenValuesWithQRTest {
  @Test
  public void BasicDiagonalMatrix() {
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
}