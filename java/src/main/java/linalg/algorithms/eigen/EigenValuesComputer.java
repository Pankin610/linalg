package linalg.algorithms.eigen;

import linalg.matrix.Matrix;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

public interface EigenValuesComputer {
  Collection<Double> GetEigenValues(Matrix mat) throws InterruptedException, ExecutionException;
}