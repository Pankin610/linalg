#include <gtest/gtest.h>
#include <exception>
#include "matrix.h"

TEST(MatrixTests, MatrixMultTest) {
    linalg::Matrix<double> m = {
        {0.0, 0.2, 3.0},
        {5.0, 1.0, 23.0},
        {4.0, 0.1, 0.0}
    };
    linalg::Matrix<double> m_sq = m.multiply(m);
    linalg::Matrix<double> expected_res = {
        {13., 0.5, 4.6},
        {97., 4.3, 38.0},
        {0.5, 0.9, 14.3}
    };

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            EXPECT_DOUBLE_EQ(m_sq[i][j], expected_res[i][j]);
        }
    }
}