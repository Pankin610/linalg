package linalg.algorithms;

import linalg.matrix.Matrix;
import org.junit.jupiter.api.Assertions;
import java.util.Collection;

public class TestingUtils {
  public static void CompareMatrixWithMatrixDelta(Matrix expected, Matrix matrix, double delta) {
    Assertions.assertEquals(expected.Rows(), matrix.Rows());
    Assertions.assertEquals(expected.Cols(), matrix.Cols());

    matrix.ForEachEntry(entry-> Assertions.assertEquals(entry.Value(),
      expected.ValueAt(entry.Row(), entry.Col()), delta));
  }

  public static void CompareMatrixWithArrayDelta(double[][] expected, Matrix matrix, double delta) {
    Assertions.assertEquals(expected.length, matrix.Rows());
    Assertions.assertEquals(expected[0].length, matrix.Cols());

    matrix.ForEachEntry(entry-> Assertions.assertEquals(entry.Value(),
      expected[entry.Row()][entry.Col()], delta));
  }

  public static void CheckUpperTriangularDelta(Matrix matrix, double delta) {
    for (int i = 1; i < matrix.Rows(); i++)
      for (int j = 0; j < i; j++)
        Assertions.assertEquals(0.0, matrix.ValueAt(i, j), delta);
  }

  public static boolean IsWithin(
    Collection<Double> a, Collection<Double> b, double tol) {
    for (Double x : a) {
      boolean within = false;
      for (Double y : b) {
        if (Math.abs(y - x) < tol) {
          within = true;
          break;
        }
      }
      if (!within) {
        return false;
      }
    }
    return true;
  }

  public static boolean SameDoubleCollections(
    Collection<Double> a, Collection<Double> b, double tol) {
    return IsWithin(a, b, tol) && IsWithin(a, b, tol);
  }
}
