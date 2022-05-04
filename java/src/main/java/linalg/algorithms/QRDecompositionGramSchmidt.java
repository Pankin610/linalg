package linalg.algorithms;

import linalg.matrix.*;
import linalg.vector.DenseVectorBuilder;
import linalg.vector.Vector;

import java.util.concurrent.atomic.AtomicReference;

public class QRDecompositionGramSchmidt {

    Matrix Q;
    Matrix R;
    Matrix matrix;

    QRDecompositionGramSchmidt(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("Illegal argument");
        }

        this.matrix = matrix;
    }


    public void Decompose() {
        Q = QRDecomposition().BuildMatrix();
        R = MatrixFactory.DenseMultiply(Transpose(Q), matrix);
    }


    /**
     * Modified and more stable approach decomposing given matrix to
     * Q - orthogonal
     * and R - upper triangular
     * matrices with Gram-Schmidt algorithm
     */
    DenseMatrixBuilder QRDecomposition() {
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
                double scalar = ScalarProduct(a_j.BuildVector(), q_i.BuildVector());
                for (int k = 0; k < a_j.VectorSize(); k++) {
                    double to_subtract = q_i.GetValue(k) * scalar;
                    double current_val = a_builder.GetValue(k, j);
                    a_builder.SetValue(k, j,  current_val - to_subtract);
                }
            }
        }

        return q_builder;
    }

    DenseVectorBuilder IthColumn(int i, MatrixBuilder matrix) {
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

    double ScalarProduct(Vector a, Vector b) {
        if (a.Size() != b.Size()) {
            throw new IllegalArgumentException("Vectors of unequal sizes");
        }

        AtomicReference<Double> result = new AtomicReference<>((double) 0);
        a.ForEachEntry(entry -> result.updateAndGet(v -> v + entry.Value() * b.ValueAt(entry.Index())));

        return result.get();
    }

    Matrix Transpose(Matrix a) {
        MatrixBuilder builder = new DenseMatrixBuilder(a.Cols(), a.Rows());
        a.ForEachEntry(entry -> builder.SetValue(entry.Col(), entry.Row(), entry.Value()));
        return builder.BuildMatrix();
    }
}
