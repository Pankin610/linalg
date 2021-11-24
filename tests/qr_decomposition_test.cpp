#include <gtest/gtest.h>

#include "qr_decomposition.h"
#include "matrix.h"
#include <exception>
#include <iostream>
#include <string>

TEST(QR_Tests, BasicTest) {
    linalg::Matrix<double> mat = {
        {-1.0, 3.0, 1.0},
        {0.0, 1.0, 1.0},
        {2.0, 1.0, -1.0}
    };
    linalg::QRDecomposition<double> decomp(mat);
    
    auto R = decomp.getR();
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < i; j++) {
            EXPECT_NEAR(R[i][j], 0.0, 1e-15);
        }
    }

    auto Q = decomp.getQ();
    auto I = Q.multiply(Q.transposed());
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (i == j) {
                EXPECT_NEAR(I[i][j], 1.0, 1e-15);
            }
            else {
                EXPECT_NEAR(I[i][j], 0.0, 1e-15);
            }
        }
    }
}