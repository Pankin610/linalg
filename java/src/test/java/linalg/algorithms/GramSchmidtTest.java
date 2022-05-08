package linalg.algorithms;

import linalg.matrix.DenseMatrixBuilder;
import linalg.matrix.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class GramSchmidtTest {

    @Test
    void Decompose() {
        double[][] arr_a = {
            {1.4, 1.7, 2.9},
            {0.7, 1.0, 3.0},
            {1.8, 4.3, 2.0}
        };

        double[][] arr_q = {
            {0.587, -0.716, -0.378},
            {0.293,	-0.246,	0.924},
            {0.755,	0.653, -0.0655}
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


        GramSchmidt res = new GramSchmidt(dmb_a.BuildMatrix());
        res.Decompose();
        Matrix Q = res.Q();
        Matrix R = res.R();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assertions.assertEquals(arr_q[i][j], Q.ValueAt(i, j), 0.01);
                Assertions.assertEquals(arr_r[i][j], R.ValueAt(i, j), 0.01);
            }
        }

    }
}