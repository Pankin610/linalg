package linalg.matrix;

import linalg.matrix.MatrixUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;

class MatrixUtilsTest {
  @Test
  public void MatrixIsSquareCorrect() {
    Matrix mat = new DenseMatrixBuilder(5, 5).BuildMatrix();
    Assertions.assertTrue(MatrixUtils.IsSquare(mat));
  }

  @Test
  public void MatrixIsNotSquareCorrect() {
    Matrix mat = new DenseMatrixBuilder(5, 6).BuildMatrix();
    Assertions.assertFalse(MatrixUtils.IsSquare(mat));
  }

  @Test
  public void EntriesByColWorksCorrectly() {
    MatrixBuilder builder = new COOMatrixBuilder(5, 5);
    int[][] cols_by_row = {
      {0, 1, 2},
      {2, 4},
      {},
      {0, 1, 2, 3, 4},
      {2}
    };
    for (int row = 0; row < 5; row++) {
      for (int col : cols_by_row[row]) {
        builder.SetValue(row, col, 1.);
      }
    }
    Matrix mat = builder.BuildMatrix();
    ArrayList<ArrayList<MatrixEntry>> entry_by_row = MatrixUtils.GetMatrixEntriesByRow(mat);
    Assertions.assertEquals(entry_by_row.size(), 5);

    Comparator<MatrixEntry> compare_by_col = new Comparator<MatrixEntry>() {      
      public int compare(MatrixEntry e1, MatrixEntry e2) {
        return Integer.compare(e1.Col(), e2.Col());
      }
    };
    for (int row = 0; row < 5; row++) {
      Assertions.assertEquals(entry_by_row.get(row).size(), cols_by_row[row].length);
      entry_by_row.get(row).sort(compare_by_col);
      for (int j = 0; j < cols_by_row[row].length; j++) {
        Assertions.assertEquals(entry_by_row.get(row).get(j), 
                                new MatrixEntry(row, cols_by_row[row][j], 1.));
      }
    }
  }
}