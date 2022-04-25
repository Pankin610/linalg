package linalg.vector;

import linalg.vector.VectorEntry;

import java.util.function.Consumer;

public interface Vector {
  int Size();
  double ValueAt(int index);
  void ForEachEntry(Consumer<VectorEntry> func);  
}