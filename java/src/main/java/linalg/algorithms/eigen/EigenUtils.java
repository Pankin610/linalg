package linalg.algorithms.eigen;

import linalg.matrix.*;
import linalg.random.RandomVectors;
import linalg.vector.*;

import java.lang.IllegalArgumentException;

public class EigenUtils {
  public static Double GetGreatestEigenValue(Matrix mat, int max_iter, double eps) {
    if (!MatrixUtils.IsSquare(mat)) {
      throw new IllegalArgumentException("Matrix isn't square.");
    }
    Vector vec = VectorFactory.Normalized(RandomVectors.RandomDenseVector(mat.Rows()));

    // Minimum number of multiplications
    for (int i = 0; i < 5; i++) {
      vec = VectorFactory.Normalized(VectorFactory.MatrixByVector(mat, vec));
    }
    while(max_iter > 0) {
      max_iter--;
      Vector new_vec = VectorFactory.MatrixByVector(mat, vec);
      double lambda = 0.;
      for (int i = 0; i < vec.Size(); i++) {
        if (Math.abs(vec.ValueAt(i)) < eps) {
          continue;
        }
        lambda = new_vec.ValueAt(i) / vec.ValueAt(i);
        break;
      }

      boolean is_eigen = true;
      for (int i = 0; i < vec.Size(); i++) {
        if (Math.abs(vec.ValueAt(i) * lambda - new_vec.ValueAt(i)) > eps) {
          is_eigen = false;
          break;
        }
      }
      if (is_eigen) {
        return lambda;
      }

      vec = VectorFactory.Normalized(new_vec);
    }
    throw new IllegalArgumentException("No eigen value.");
  }
}