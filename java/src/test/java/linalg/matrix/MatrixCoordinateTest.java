package linalg.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

class MatrixCoordinateTest {
  final int test_size = 100;

  @Test
  void RowMethodTest() {
    for (int i = 0; i < test_size; i++)
      Assertions.assertEquals(i, new MatrixCoordinate(i, i + new Random().nextInt(test_size)).Row());
  }

  @Test
  void ColMethodTest() {
    for (int i = 0; i < test_size; i++)
      Assertions.assertEquals(i, new MatrixCoordinate( i + new Random().nextInt(test_size), i).Col());
  }

  @Test
  void EqualsSimpleTest() {
    for (int i = 0; i < test_size; i++)
      Assertions.assertEquals(new MatrixCoordinate(i, i), new MatrixCoordinate(i , i));
    for (int i = 0; i < test_size; i++)
      Assertions.assertNotEquals(new MatrixCoordinate(i, i), new MatrixCoordinate(i, i + 1));
    MatrixCoordinate[] arr = new MatrixCoordinate[test_size];
    for (int i = 0; i < test_size; i++)
      arr[i] = new MatrixCoordinate(10, 100);
    for (MatrixCoordinate coord1 : arr)
      for (MatrixCoordinate coord2 : arr)
        Assertions.assertEquals(coord1, coord2);
  }

  @Test
  void HashCodeSimpleTest() {
    for (int i = 0; i < test_size; i++)
      Assertions.assertEquals(new MatrixCoordinate(i, i).hashCode(), new MatrixCoordinate(i , i).hashCode());
    MatrixCoordinate[] arr = new MatrixCoordinate[test_size];
    for (int i = 0; i < test_size; i++)
      arr[i] = new MatrixCoordinate(10, 100);
    for (MatrixCoordinate coord1 : arr)
      for (MatrixCoordinate coord2 : arr)
        Assertions.assertEquals(coord1.hashCode(), coord2.hashCode());
  }
}