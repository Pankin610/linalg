#include <gtest/gtest.h>
#include "lu_decomposition.h"
#include "matrix.h"
#include <exception>
#include <iostream>
#include <string>

TEST(BasicTest, LU_Tests) {
    linalg::Matrix<double> mat = {
        {-1.0, 3.0, 1.0},
        {0.0, 1.0, 1.0},
        {2.0, 1.0, -1.0}
    };
    linalg::LUDecomposition<double> decomp(mat, 1.0);
    auto L = decomp.getL();
    auto U = decomp.getU();
    auto p = decomp.getPermutation();

    for (int i = 0; i < 3; i++) {
        for (int j = i + 1; j < 3; j++) {
            EXPECT_DOUBLE_EQ(L[p[i]][j], 0.0);
        }
    }
    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < i; j++) {
            EXPECT_DOUBLE_EQ(U[p[i]][j], 0.0);
        }
    }

    std::vector<linalg::Vector<double>> equations = {
        {-5.0, 2.0, 0.0},
        {0.0, 0.0, 0.0},
        {3.0, -7.0, 6.0}
    };
    std::vector<linalg::Vector<double>> expected_res = {
        {3.0, -2.0, 4.0},
        {0.0, 0.0, 0.0},
        {-3.6666666667, 3.1666666667, -10.16666666667}
    };

    for (int e = 0; e < 3; e++) {
        auto res = decomp.solveForVector(equations[e]);
        for (int i = 0; i < 3; i++) {
            EXPECT_NEAR(res[i], expected_res[e][i], 1e-6);
        }
    }
}