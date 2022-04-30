package linalg.vector;

import linalg.vector.Vector;
import java.lang.IllegalArgumentException;

abstract class VectorBuilder {
  public VectorBuilder(int size) {
    size_ = size;
  }

  public int VectorSize() {
    return size_;
  }

  abstract protected void SetValueSafe(int index, double value);

  public void SetValue(int index, double value) {
    if (index < 0 || index >= VectorSize()) {
      throw new IllegalArgumentException("Index out of range");
    }
    SetValueSafe(index, value);
  }

  abstract protected double GetValueSafe(int index);

  public double GetValue(int index) {
    if (index < 0 || index >= VectorSize()) {
      throw new IllegalArgumentException("Index out of range");
    }
    return GetValueSafe(index);
  }

  abstract public Vector BuildVector();

  private int size_;
}