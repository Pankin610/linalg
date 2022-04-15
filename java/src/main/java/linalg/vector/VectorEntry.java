package linalg.vector;

public class VectorEntry {
  public VectorEntry(int index, double value) {
    index_ = index;
    value_ = value;
  } 

  public int Index() {
    return index_;
  }

  public double Value() {
    return value_;
  }

  private int index_;
  private double value_;
}