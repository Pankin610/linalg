package linalg.vector;

import linalg.vector.Vector;
import java.util.concurrent.atomic.AtomicReference;
import java.lang.Math;
import java.lang.IllegalArgumentException;

class VectorUtils {
  public static double InnerProduct(Vector v) {
    AtomicReference<Double> prod = new AtomicReference<>(0.);
    v.ForEachEntry(entry -> {
      prod.accumulateAndGet(entry.Value() * entry.Value(), (a, b) -> a + b);
    });
    return prod.get();
  }

  public static double VectorLength(Vector v) {
    return Math.sqrt(InnerProduct(v));
  }

  public static double OuterProduct(Vector a, Vector b) {
    if (a.Size() != b.Size()) {
      throw new IllegalArgumentException("Different vector sizes.");
    }
    AtomicReference<Double> prod = new AtomicReference<>(0.);
    a.ForEachEntry(entry -> {
      prod.accumulateAndGet(entry.Value() * b.ValueAt(entry.Index()), (x, y) -> x + y);
    });
    return prod.get();
  }
}