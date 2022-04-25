package linalg.matrix;

import linalg.matrix.MatrixCoordinate;
import java.util.Objects;

public class MatrixEntry {
  public MatrixEntry(int x, int y, double value) {
    coord_ = new MatrixCoordinate(x, y);
    value_ = value;
  }

  public MatrixEntry(MatrixCoordinate coord, double value) {
    coord_ = coord;
    value_ = value;
  }

  public int Row() {
    return coord_.Row();
  }

  public int Col() {
    return coord_.Col();
  }

  public double Value() {
    return value_;
  }

  public MatrixCoordinate Coord() {
    return coord_;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof MatrixEntry)) {
      return false;
    }
    MatrixEntry other = (MatrixEntry)o;
    return other.Coord().equals(Coord()) && Value() == other.Value();
  }

  @Override
  public int hashCode() {
    return Objects.hash(Coord(), Value());
  }

  private MatrixCoordinate coord_;
  private double value_;
}