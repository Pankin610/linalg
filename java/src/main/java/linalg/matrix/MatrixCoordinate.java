package linalg.matrix;

import java.util.Objects;

public class MatrixCoordinate {
  public MatrixCoordinate(int x, int y) {
    x_ = x;
    y_ = y;
  }

  public int Row() {
    return x_;
  }

  public int Col() {
    return y_;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof MatrixCoordinate)) {
      return false;
    }
    MatrixCoordinate other = (MatrixCoordinate)o;
    return Row() == other.Row() && Col() == other.Col();
  }

  @Override
  public int hashCode() {
    return Objects.hash(Row(), Col());
  }

  private int x_;
  private int y_;
}