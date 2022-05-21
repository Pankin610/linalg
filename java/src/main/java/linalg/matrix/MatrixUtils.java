package linalg.matrix;

import linalg.matrix.Matrix;

public class MatrixUtils {
  public static boolean IsSquare(Matrix mat) {
    return mat.Rows() == mat.Cols();
  }
}