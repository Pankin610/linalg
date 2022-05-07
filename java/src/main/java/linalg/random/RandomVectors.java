package linalg.random;

import java.util.Random;

import linalg.vector.DenseVector;
import linalg.vector.DenseVectorBuilder;
import linalg.vector.Vector;

public class RandomVectors {
  public static DenseVector RandomDenseVector(int size) {
    DenseVectorBuilder builder = new DenseVectorBuilder(size);
    for (int i = 0; i < size; i++) {
      builder.SetValue(i, rng.nextDouble());
    }
    return (DenseVector)builder.BuildVector();
  }

  private static Random rng = new Random();
}