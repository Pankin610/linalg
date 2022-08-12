package linalg.algorithm.comp;

import java.time.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import linalg.matrix.Matrix;

public class TimeMeasureUtil {
  public static  <T extends Matrix> Duration MeasureTime(T mat, Consumer<T> algo) {
    Instant start = Instant.now();
    algo.accept(mat);
    Instant end = Instant.now(); 
    return Duration.between(start, end);
  }
}