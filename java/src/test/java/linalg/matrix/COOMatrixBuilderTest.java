package linalg.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class COOMatrixBuilderTest {

  @Test
  void SetValueWithExceptionHandlingWhileBadParamsSentToConstructor() {
    String expected_string = "Wrong matrix dimensions";

    for (int i = -10; i <= 10; i++) {
      for (int j = -10; j <= 10; j++) {
        try {
          new COOMatrixBuilder(i, j);
        } catch (IllegalArgumentException e) {
          if (i > 0 && j > 0)
            Assertions.fail();
          Assertions.assertEquals(expected_string, e.getMessage());
          continue;
        }
        if (i <= 0 || j <= 0)
          Assertions.fail();
        COOMatrixBuilder coo_matrix_builder = new COOMatrixBuilder(i, j);
        MatrixCoordinate coords = new MatrixCoordinate(new Random().nextInt(i), new Random().nextInt(j));
        coo_matrix_builder.SetValue(coords.Row(), coords.Col(), (double) (coords.Col() * coords.Row()));
        Assertions.assertEquals(coords.Row() * coords.Col(), coo_matrix_builder.entry_map_.get(coords));
      }
    }
  }

  @Test
  void GetValueWithExceptionHandlingWhileBadParamsSentToConstructor() {
    String expected_string = "Wrong matrix dimensions";

    for (int i = -10; i <= 10; i++) {
      for (int j = -10; j <= 10; j++) {
        try {
          new COOMatrixBuilder(i, j);
        } catch (IllegalArgumentException e) {
          if (i > 0 && j > 0)
            Assertions.fail();
          Assertions.assertEquals(expected_string, e.getMessage());
          continue;
        }
        if (i <= 0 || j <= 0)
          Assertions.fail();
        COOMatrixBuilder coo_matrix_builder = new COOMatrixBuilder(i, j);
        MatrixCoordinate coords = new MatrixCoordinate(new Random().nextInt(i), new Random().nextInt(j));
        coo_matrix_builder.entry_map_.put(new MatrixCoordinate(i, j), (double) (coords.Col() * coords.Row()));
        Assertions.assertEquals(coords.Row() * coords.Col(), coo_matrix_builder.GetValue(coords.Row(), coords.Col()));
      }
    }
  }

  @Test
  void BuildMatrixComparedWithManuallyCreatedObject() {
    final int test_size = 100;

    DenseMatrixBuilder dmb = new DenseMatrixBuilder(test_size, test_size);
    for (int i = 0; i < test_size; i++)
      for (int j = 0; j < test_size; j++)
        dmb.SetValue(i, j, i * test_size + j);

    DenseMatrix matrix = (DenseMatrix) dmb.BuildMatrix();
    Map<MatrixCoordinate, Double> map = new HashMap<>();

    matrix.ForEachEntry(entry -> map.put(entry.Coord(), entry.Value()));
    COOMatrix coo_matrix1 = new COOMatrix(map, test_size, test_size);

    COOMatrixBuilder coo_matrix_builder = new COOMatrixBuilder(test_size, test_size);
    for (int i = 0; i < test_size; i++)
      for (int j = 0; j < test_size; j++)
        coo_matrix_builder.SetValue(i, j, i * test_size + j);

    COOMatrix coo_matrix2 = (COOMatrix) coo_matrix_builder.BuildMatrix();

    Assertions.assertEquals(coo_matrix1.Cols(), coo_matrix2.Cols());
    Assertions.assertEquals(coo_matrix1.Rows(), coo_matrix2.Rows());

    for (int i = 0; i < test_size; i++)
      for (int j = 0; j < test_size; j++)
        Assertions.assertEquals(coo_matrix1.ValueAt(i , j), coo_matrix2.ValueAt(i, j));
  }
}