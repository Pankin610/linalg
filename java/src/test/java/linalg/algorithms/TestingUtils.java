package linalg.algorithms;

import static linalg.matrix.MatrixFactory.IdentityMatrix;

import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import linalg.matrix.MatrixFactory;

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

  public static void QRDecomposeDoubleArray(double[][] arr, QRDecomposition decomposition, double delta) {
    DenseMatrixBuilder dmb_a = new DenseMatrixBuilder(arr.length, arr[0].length);
    for (int i = 0; i < arr.length; i++)
      for (int j = 0; j < arr[0].length; j++)
        dmb_a.SetValue(i, j, arr[i][j]);

    decomposition.Decompose(dmb_a.BuildMatrix());

    Matrix Q = decomposition.GetQ();
    Matrix R = decomposition.GetR();

    Matrix id = MatrixFactory.DenseMultiply(Q, Q.Transpose());
    Matrix res = MatrixFactory.DenseMultiply(Q, R);

    Assertions.assertEquals(id.Rows(), id.Cols());
    Assertions.assertEquals(arr.length, res.Rows());
    Assertions.assertEquals(arr[0].length, res.Cols());

    CompareMatrixWithMatrixDelta(IdentityMatrix(id.Cols()), id, delta);
    CheckUpperTriangularDelta(R, delta);
    CompareMatrixWithArrayDelta(arr, res, delta);
  }
}
