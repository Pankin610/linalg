package linalg.matrix;

import linalg.matrix.Matrix;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.ArrayList;

public class MatrixUtils {
  public static boolean IsSquare(Matrix mat) {
    return mat.Rows() == mat.Cols();
  }

  public static ArrayList<ArrayList<MatrixEntry>> GetMatrixEntriesByRow(Matrix mat) {
    ArrayList<ArrayList<MatrixEntry>> row_list = new ArrayList<ArrayList<MatrixEntry>>();
    for (int i = 0; i < mat.Rows(); i++) {
      row_list.add(new ArrayList<MatrixEntry>());
    }
    mat.ForEachEntry(entry -> {
      row_list.get(entry.Row()).add(entry);
    });
    return row_list;
  }
}