package linalg.vector;

import linalg.vector.Vector;
import linalg.vector.DenseVector;
import linalg.vector.DenseVectorBuilder;
import linalg.matrix.Matrix;

public class VectorFactory {
  public static Vector Zeros(int size) {
    VectorBuilder builder = new DenseVectorBuilder(size);
    return builder.BuildVector();
  }

  public static Vector Ones(int size) {
    VectorBuilder builder = new DenseVectorBuilder(size);
    for (int i = 0; i < size; i++) {
      builder.SetValue(i, 1);
    }
    return builder.BuildVector();
  }

  public static Vector MatrixByVector(Matrix mat, Vector vec) {
    if (vec.Size() != mat.Cols()) {
      throw new IllegalArgumentException("Wrong vector dimension.");
    }
    VectorBuilder builder = new DenseVectorBuilder(mat.Rows());
    mat.ForEachEntry(entry -> {
      double cur_value = builder.GetValue(entry.Row());
      builder.SetValue(entry.Row(), cur_value + entry.Value() * vec.ValueAt(entry.Col()));
    });
    return builder.BuildVector();
  }


  public static Vector Normalized(Vector vec) {
    VectorBuilder builder = new DenseVectorBuilder(vec.Size());
    final double len = VectorUtils.VectorLength(vec);
    vec.ForEachEntry(entry -> {
      builder.SetValue(entry.Index(), entry.Value() / len);
    });
    return builder.BuildVector();
  }
}