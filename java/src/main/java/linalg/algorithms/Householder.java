package linalg.algorithms;

import linalg.matrix.DenseMatrix;
import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import linalg.matrix.MatrixFactory;
import linalg.vector.DenseVectorBuilder;

import static linalg.matrix.MatrixFactory.*;
import static linalg.vector.VectorUtils.VectorLength;

public class Householder implements QRDecomposition {

  private Matrix Q;
  private Matrix R;

  @Override
  public Matrix GetQ() {
    return Q;
  }

  @Override
  public Matrix GetR() {
    return R;
  }

  @Override
  public void Decompose(Matrix matrix) {
    HouseholderAlgorithm(matrix);
    R = MatrixFactory.DenseMultiply(Q.Transpose(), matrix);
  }

  private void HouseholderAlgorithm(Matrix A) {
    if (A.Cols() == 0)
      return;

    DenseVectorBuilder a_vec_builder = FirstColumn(A);

    a_vec_builder.SetValue(0, a_vec_builder.GetValue(0) +
      VectorLength(a_vec_builder.BuildVector()) * Math.signum(a_vec_builder.GetValue(0)));

    Matrix identity = IdentityMatrix(a_vec_builder.VectorSize());
    Matrix fraction = FractionOfVectors(a_vec_builder);

    Matrix H = Add(identity, fraction);
    Q = (Q == null) ? H : DenseMultiply(Q, UnitColRows(H));

    if (A.Cols() > 1 && A.Rows() > 1)
      HouseholderAlgorithm(CutFirstColRow(DenseMultiply(H, A)));
  }

  private DenseVectorBuilder FirstColumn(Matrix matrix) {
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix cannot be null");
    }

    DenseVectorBuilder builder = new DenseVectorBuilder(matrix.Rows());

    for (int i = 0; i < builder.VectorSize(); i++) {
      builder.SetValue(i, matrix.ValueAt(i, 0));
    }

    return builder;
  }


  /**
   *
   * @param dvb - vertical vector v
   * @return - fraction (v*vT)/(vT*v)
   * where vT is transposition of v
   */
  private Matrix FractionOfVectors(DenseVectorBuilder dvb) {
    DenseMatrixBuilder vertical = new DenseMatrixBuilder(dvb.VectorSize(), 1);
    for (int i = 0; i < dvb.VectorSize(); i++) {
      vertical.SetValue(i, 0, dvb.GetValue(i));
    }
    Matrix horizontal = vertical.BuildMatrix().Transpose();

    DenseMatrix matrix = DenseMultiply(vertical.BuildMatrix(), horizontal);
    DenseMatrix val = DenseMultiply(horizontal, vertical.BuildMatrix());
    double div = val.ValueAt(0, 0);

    DenseMatrixBuilder dmb = new DenseMatrixBuilder(dvb.VectorSize(), dvb.VectorSize());
    for (int i = 0; i < dvb.VectorSize(); i++) {
      for (int j = 0; j < dvb.VectorSize(); j++) {
        dmb.SetValue(i, j, -matrix.ValueAt(i, j) * 2 / div);
        if (Double.isNaN(dmb.GetValue(i, j)))
          dmb.SetValue(i, j, 0.0);
      }
    }
    
    return dmb.BuildMatrix();
  }

  /**
   * @return matrix without first column and first row
   */
  Matrix CutFirstColRow(Matrix matrix) {
    DenseMatrixBuilder dmb = new DenseMatrixBuilder(matrix.Rows() - 1, matrix.Cols() - 1);

    matrix.ForEachEntry(entry -> {
      if (entry.Col() > 0 && entry.Row() > 0)
        dmb.SetValue(entry.Row() - 1, entry.Col() - 1, entry.Value());
    });

    return dmb.BuildMatrix();
  }

  /**
   *
   * @param mat - matrix with probably smaller size than Q
   * @return matrix of Q's size and mat entries with
   * additional rows and cols with 1.0s diagonal
   */
  Matrix UnitColRows(Matrix mat) {
    DenseMatrixBuilder builder = new DenseMatrixBuilder(Q.Rows(), Q.Cols());
    int diff = Q.Rows() - mat.Rows();

    mat.ForEachEntry(entry-> builder.SetValue(entry.Row() + diff, entry.Col() + diff, entry.Value()));

    for (int i = 0; i < diff; i++)
      builder.SetValue(i, i, 1.0);

    return builder.BuildMatrix();
  }
}
