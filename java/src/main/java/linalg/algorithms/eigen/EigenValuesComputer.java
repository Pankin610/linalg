package linalg.algorithms.eigen;

import linalg.matrix.Matrix;
import java.util.Collection;

interface EiganValuesComputer {
  Collection<Double> GetEigenValues(Matrix mat);
}