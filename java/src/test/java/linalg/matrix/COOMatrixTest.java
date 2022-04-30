package linalg.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

class COOMatrixTest {
  final int test_size = 100;

  @Test
  void RowsWithCorrectEntriesAndNonEmptyMap() {
    DenseMatrixBuilder dmb = new DenseMatrixBuilder(test_size, test_size);
    for (int i = 0; i < test_size; i++)
      for (int j = 0; j < test_size; j++)
        dmb.SetValue(i, j, i * test_size + j);

    DenseMatrix matrix = (DenseMatrix) dmb.BuildMatrix();
    Map<MatrixCoordinate, Double> map = new HashMap<>();

    matrix.ForEachEntry(entry -> map.put(entry.Coord(), entry.Value()));

    COOMatrix coo_matrix = new COOMatrix(map, test_size, test_size);
    Assertions.assertEquals(coo_matrix.Rows(), test_size);
  }

  @Test
  void RowsWithEmptyMap() {
    for (int i = 0; i < test_size; i++)
      Assertions.assertEquals(i, new COOMatrix(new HashMap<>(), i, i + new Random().nextInt(test_size)).Rows());
  }

  @Test
  void ColumnsWithCorrectEntriesAndNonEmptyMap() {
    DenseMatrixBuilder dmb = new DenseMatrixBuilder(test_size, test_size);
    for (int i = 0; i < test_size; i++)
      for (int j = 0; j < test_size; j++)
        dmb.SetValue(i, j, i * test_size + j);

    DenseMatrix matrix = (DenseMatrix) dmb.BuildMatrix();
    Map<MatrixCoordinate, Double> map = new HashMap<>();

    matrix.ForEachEntry(entry -> map.put(entry.Coord(), entry.Value()));

    COOMatrix coo_matrix = new COOMatrix(map, test_size, test_size);
    Assertions.assertEquals(coo_matrix.Cols(), test_size);
  }

  @Test
  void ColumnsWithEmptyMap() {
    for (int i = 0; i < test_size; i++)
      Assertions.assertEquals(i, new COOMatrix(new HashMap<>(), i + new Random().nextInt(test_size), i).Cols());
  }

  @Test
  void ValueAtForSquareMatrix() {
    DenseMatrixBuilder dmb = new DenseMatrixBuilder(test_size, test_size);
    for (int i = 0; i < test_size; i++)
      for (int j = 0; j < test_size; j++)
        dmb.SetValue(i, j, i * test_size + j);

    DenseMatrix matrix = (DenseMatrix) dmb.BuildMatrix();
    Map<MatrixCoordinate, Double> map = new HashMap<>();

    matrix.ForEachEntry(entry -> map.put(entry.Coord(), entry.Value()));
    COOMatrix coo_matrix = new COOMatrix(map, test_size, test_size);

    for (int i = 0; i < test_size; i++)
      for (int j = 0; j < test_size; j++)
        Assertions.assertEquals(i * test_size + j, coo_matrix.ValueAt(i, j));
  }

  @Test
  void AddingEachValueOfEntryToListAndCheckingTheResult() {
    DenseMatrixBuilder dmb = new DenseMatrixBuilder(test_size, test_size);
    for (int i = 0; i < test_size; i++)
      for (int j = 0; j < test_size; j++)
        dmb.SetValue(i, j, i * test_size + j);

    DenseMatrix matrix = (DenseMatrix) dmb.BuildMatrix();
    Map<MatrixCoordinate, Double> map = new HashMap<>();

    matrix.ForEachEntry(entry -> map.put(entry.Coord(), entry.Value()));
    COOMatrix coo_matrix = new COOMatrix(map, test_size, test_size);

    LinkedList<Integer> list = new LinkedList<>();
    coo_matrix.ForEachEntry(entry -> list.add((int) entry.Value()));

    for (int i = 0; i < test_size; i++)
      for (int j = 0; j < test_size; j++)
        Assertions.assertTrue(list.contains((int) coo_matrix.ValueAt(i, j)));

    Assertions.assertEquals(list.size(), test_size * test_size);
  }
}