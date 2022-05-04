package linalg.algorithms;

import linalg.matrix.DenseMatrix;
import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import linalg.vector.DenseVectorBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QRDecompositionGramSchmidtTest {

    @Test
    void Decompose() {
        double[][] arr_a = {
            {1.4, 1.7, 2.9},
            {0.7, 1.0, 3.0},
            {1.8, 4.3, 2.0}
        };

        double[][] arr_q = {
            {0.587, -0.716, -0.378},
            {0.293, -0.246, 0.924},
            {0.755, 0.653, -0.0655}
        };

        double[][] arr_r = {
            {2.39, 4.54, 4.09},
            {0, 1.34, -1.51},
            {0,	0, 1.54}
        };

        DenseMatrixBuilder dmb_a = new DenseMatrixBuilder(3, 3);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                dmb_a.SetValue(i, j, arr_a[i][j]);


        QRDecompositionGramSchmidt res = new QRDecompositionGramSchmidt(dmb_a.BuildMatrix());
        res.Decompose();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assertions.assertEquals(arr_q[i][j], res.Q.ValueAt(i, j), 0.01);
                Assertions.assertEquals(arr_r[i][j], res.R.ValueAt(i, j), 0.01);
            }
        }

    }

    @Test
    void QRDecomposition() {
        double[][] arr_a = {
            {1.4, 1.7, 2.9},
            {0.7, 1.0, 3.0},
            {1.8, 4.3, 2.0}
        };

        double[][] arr_q = {
            {0.587, -0.716, -0.378},
            {0.293, -0.246, 0.924},
            {0.755, 0.653, -0.0655}
        };

        DenseMatrixBuilder dmb_a = new DenseMatrixBuilder(3, 3);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                dmb_a.SetValue(i, j, arr_a[i][j]);


        DenseMatrixBuilder res = new QRDecompositionGramSchmidt(dmb_a.BuildMatrix()).QRDecomposition();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                Assertions.assertEquals(arr_q[i][j], res.GetValue(i, j), 0.01);
    }

    @Test
    void IthColumn() {
        double[][] arr1 = {
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };
        DenseMatrixBuilder dmb1 = new DenseMatrixBuilder(3, 3);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                dmb1.SetValue(i, j, arr1[i][j]);

        QRDecompositionGramSchmidt qr = new QRDecompositionGramSchmidt(dmb1.BuildMatrix());
        DenseVectorBuilder dvb0 = qr.IthColumn(0, dmb1);
        DenseVectorBuilder dvb1 = qr.IthColumn(1, dmb1);
        DenseVectorBuilder dvb2 = qr.IthColumn(2, dmb1);

        for (int i = 0; i < 3; i++) {
            Assertions.assertEquals(1 / Math.sqrt(3), dvb0.GetValue(i));
            Assertions.assertEquals(2 / Math.sqrt(12), dvb1.GetValue(i));
            Assertions.assertEquals(3 / Math.sqrt(27), dvb2.GetValue(i));
        }
    }

    @Test
    void ScalarProduct() {
        DenseVectorBuilder builder = new DenseVectorBuilder(4);
        for (int i = 0; i < builder.VectorSize(); i++)
            builder.SetValue(i, 5);

        DenseMatrixBuilder dmb = new DenseMatrixBuilder(1, 1);
        dmb.SetValue(0, 0,0);

        double res = new QRDecompositionGramSchmidt(dmb.BuildMatrix()).
                ScalarProduct(builder.BuildVector(), builder.BuildVector());

        Assertions.assertEquals((double) 100, res);
    }

    @Test
    void TransposeSimpleTestFor3x3Matrix() {
        double[][] arr1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        DenseMatrixBuilder dmb1 = new DenseMatrixBuilder(3, 3);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                dmb1.SetValue(i, j, arr1[i][j]);

        QRDecompositionGramSchmidt qr = new QRDecompositionGramSchmidt(dmb1.BuildMatrix());
        Matrix mat = qr.Transpose(qr.matrix);

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                Assertions.assertEquals(mat.ValueAt(i, j), dmb1.GetValue(j, i));
    }
}
