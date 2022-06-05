package linalg.matrix;

import linalg.matrix.Matrix;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.ArrayList;

public class MatrixUtils {
  public static boolean IsSquare(Matrix mat) {
    return mat.Rows() == mat.Cols();
  }

  public static ArrayList<ArrayList<MatrixEntry>> GetMatrixEntriesByCol(Matrix mat) {
    ArrayList<ArrayList<MatrixEntry>> col_list = new ArrayList<ArrayList<MatrixEntry>>();
    for (int i = 0; i < mat.Cols(); i++) {
      col_list.add(new ArrayList<MatrixEntry>());
    }
    mat.ForEachEntry(entry -> {
      col_list.get(entry.Col()).add(entry);
    });
    return col_list;
  }
}