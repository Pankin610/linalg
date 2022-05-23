package linalg.algorithms.eigen;

import linalg.matrix.Matrix;
import java.util.Collection;

public interface EigenValuesComputer {
  Collection<Double> GetEigenValues(Matrix mat);
}