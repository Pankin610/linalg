package linalg.matrix;

public class MatrixEntry {
  public MatrixEntry(int x, int y, double value) {
    x_ = x;
    y_ = y;
    value_ = value;
  }

  public int Row() {
    return x_;
  }

  public int Col() {
    return y_;
  }

  public double Value() {
    return value_;
  }

  private int x_;
  private int y_;
  private double value_;
}