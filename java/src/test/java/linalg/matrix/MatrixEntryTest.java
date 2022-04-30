package linalg.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class MatrixEntryTest {

  @Test
  void RowSmallListOfEntries() {
    LinkedList<MatrixEntry> list_of_entries = new LinkedList<>();
    for (int i = 0; i < 20; i++)
      list_of_entries.add(new MatrixEntry(i, i + 1, i + 2));
    for (int i = 0; i < list_of_entries.size(); i++)
      Assertions.assertEquals(i, list_of_entries.get(i).Row());
  }

  @Test
  void ColSmallListOfEntries() {
    LinkedList<MatrixEntry> list_of_entries = new LinkedList<>();
    for (int i = 0; i < 20; i++)
      list_of_entries.add(new MatrixEntry(i, i + 1, i + 2));
    for (int i = 0; i < list_of_entries.size(); i++)
      Assertions.assertEquals(i + 1, list_of_entries.get(i).Col());
  }

  @Test
  void ValueSmallListOfEntriesIntegers() {
    LinkedList<MatrixEntry> list_of_entries = new LinkedList<>();
    for (int i = 0; i < 20; i++)
      list_of_entries.add(new MatrixEntry(i, i + 1, i + 2));
    for (int i = 0; i < list_of_entries.size(); i++)
      Assertions.assertEquals(i + 2, list_of_entries.get(i).Col());
  }

  @Test
  void ValueSmallListOfEntriesDoubles() {
    LinkedList<MatrixEntry> list_of_entries = new LinkedList<>();
    for (int i = 0; i < 20; i++)
      list_of_entries.add(new MatrixEntry(i, i + 1, i + 0.5));
    for (int i = 0; i < list_of_entries.size(); i++)
      Assertions.assertEquals(i + 0.5, list_of_entries.get(i).Value());
  }

  @Test
  void CoordSimpleCheckOfEquality() {
    MatrixEntry entry1 = new MatrixEntry(0, 0, 0);
    MatrixEntry entry2 = new MatrixEntry(0, 0, 10);
    MatrixEntry entry3 = new MatrixEntry(1, 1, 0);
    Assertions.assertEquals(entry1.Coord(), entry2.Coord());
    Assertions.assertNotEquals(entry1.Coord(), entry3.Coord());
  }

  @Test
  void EqualsSimpleTestWithSmallAmountOfEntries() {
    MatrixEntry entry1 = new MatrixEntry(0, 0, 0);
    MatrixEntry entry2 = new MatrixEntry(1, 1, 0);
    MatrixEntry entry3 = new MatrixEntry(0, 0, 1);
    MatrixEntry entry4 = new MatrixEntry(0, 0, 0.0);
    Assertions.assertEquals(entry1, entry4);
    Assertions.assertNotEquals(entry1, entry2);
    Assertions.assertNotEquals(entry1, entry3);
    Assertions.assertNotEquals(entry2, entry3);
    Assertions.assertNotEquals(entry2, entry4);
    Assertions.assertNotEquals(entry3, entry4);
  }

  @Test
  void EqualsSimpleTestWithBigAmountOfEntriesTwoAdjacentShouldBeEqual() {
    MatrixEntry[] arr = new MatrixEntry[100];
    for (int i = 0; i < arr.length; i++)
      arr[i] = new MatrixEntry(i / 2, i / 2, 0);
    for (int i = 0; i < arr.length; i += 2)
      Assertions.assertEquals(arr[i], arr[i + 1]);
  }

  @Test
  void HashCodeEqualityToAdjacentShouldBeEqual() {
    MatrixEntry[] arr = new MatrixEntry[100];
    for (int i = 0; i < arr.length; i++)
      arr[i] = new MatrixEntry(i / 2, i / 2, 0);
    for (int i = 0; i < arr.length; i += 2)
      Assertions.assertEquals(arr[i].hashCode(), arr[i + 1].hashCode());
  }
}