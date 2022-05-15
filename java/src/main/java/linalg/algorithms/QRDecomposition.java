package linalg.algorithms;

import linalg.matrix.Matrix;

public interface QRDecomposition {
    Matrix Q();
    Matrix R();
    void Decompose();
}
