package linalg.vector;

import linalg.vector.Vector;
import linalg.vector.VectorEntry;
import java.util.function.Consumer;

import java.lang.IllegalArgumentException;

class DenseVector implements Vector {
  @Override
  public double ValueAt(int index) {
    if (index < 0 || index >= values_.length) {
      throw new IllegalArgumentException("Index out of bounds.");
    }
    return values_[index];
  }

  @Override
  public int Size() {
    return values_.length;
  }

  @Override
  public void ForEachEntry(Consumer<VectorEntry> func) {
    for (int i = 0; i < Size(); i++) {
      func.accept(new VectorEntry(i, values_[i]));
    }
  }

  private double[] values_;
}