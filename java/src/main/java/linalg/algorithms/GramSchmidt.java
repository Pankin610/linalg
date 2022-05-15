package linalg.algorithms;

import linalg.matrix.*;
import linalg.vector.DenseVectorBuilder;
import linalg.algorithm.comp.AlgorithmStats;
import linalg.random.RandomMatrices;

import static linalg.vector.VectorUtils.OuterProduct;

public class GramSchmidt implements QRDecomposition {

  private Matrix Q;
  private Matrix R;
  private final Matrix matrix;

  GramSchmidt(Matrix matrix) {
    if (matrix == null) {
      throw new IllegalArgumentException("Illegal argument");
    }

    this.matrix = matrix;
  }

  @Override
  public void Decompose() {
    Q = GramSchmidtAlgorithm().BuildMatrix();
    R = MatrixFactory.DenseMultiply(Q.Transpose(), matrix);
  }

  @Override
  public Matrix Q() {
    return Q;
  }

  @Override
  public Matrix R() {
    return R;
  }


  /**
   * Modified and more stable approach decomposing given matrix to
   * Q - orthogonal
   * and R - upper triangular
   * matrices with Gram-Schmidt algorithm
   */
  private DenseMatrixBuilder GramSchmidtAlgorithm() {
    MatrixBuilder a_builder = new DenseMatrixBuilder(matrix.Rows(), matrix.Cols());
    matrix.ForEachEntry(entry -> a_builder.SetValue(entry.Row(), entry.Col(), entry.Value()));

    DenseMatrixBuilder q_builder = new DenseMatrixBuilder(matrix.Rows(), matrix.Cols());

    for (int i = 0; i < matrix.Cols(); i++) {
      DenseVectorBuilder q_i = IthColumn(i, a_builder);
      for (int j = 0; j < q_i.VectorSize(); j++)
        q_builder.SetValue(j, i, q_i.GetValue(j));

      for (int j = i + 1; j < matrix.Cols(); j++) {
        DenseVectorBuilder a_j = new DenseVectorBuilder(matrix.Rows());
        for (int iter = 0; iter < a_j.VectorSize(); iter++)
          a_j.SetValue(iter, a_builder.GetValue(iter, j));
        double scalar = OuterProduct(a_j.BuildVector(), q_i.BuildVector());
        for (int k = 0; k < a_j.VectorSize(); k++) {
          double to_subtract = q_i.GetValue(k) * scalar;
          double current_val = a_builder.GetValue(k, j);
          a_builder.SetValue(k, j,  current_val - to_subtract);
        }
      }
    }

    return q_builder;
  }

  private DenseVectorBuilder IthColumn(int i, MatrixBuilder matrix) {
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix can not be null");
    }

    double norm = 0;
    DenseVectorBuilder builder = new DenseVectorBuilder(matrix.BuildMatrix().Rows());

    for (int j = 0; j < builder.VectorSize(); j++) {
      norm += matrix.GetValue(j, i) * matrix.GetValue(j, i);
      builder.SetValue(j, matrix.GetValue(j, i));
    }

    norm = Math.sqrt(norm);

    for (int j = 0; j < builder.VectorSize(); j++) {
      builder.SetValue(j, builder.GetValue(j) / norm);
    }

    return builder;
  }

  public static void main(String[] args) {
    AlgorithmStats qr_stats = AlgorithmStats.GetAlgoStats(
      RandomMatrices.DenseMatrixSupplier(100, 100), 
      (matrix) -> {
        QRDecomposition decomp = new GramSchmidt(matrix);
        decomp.Decompose();
      },
      100);
    System.out.println(qr_stats.AverageRunTime().toMillis());
  }
}
