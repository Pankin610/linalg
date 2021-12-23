#include <gtest/gtest.h>

#include "matrix.h"
#include "rng.h"
#include <thread>

void checkIfZero(const linalg::Matrix<double>& mat) {
    for (int i = 0; i < mat.rows(); i++) {
        for (int j = 0; j < mat.cols(); j++) {
            EXPECT_DOUBLE_EQ(mat[i][j], 0.0);
        }
    }
}

void testRowMatrixWriting(linalg::Matrix<double> mat) {
    std::vector<std::thread> row_threads;
    for (int i = 0; i < mat.rows(); i++) {
        row_threads.emplace_back([&mat, i]{
            for (int j = 0; j < mat.cols(); j++) {
                mat[i][j] = 0.0;
            }
        });
    }

    for (std::thread& t : row_threads) {
        t.join();
    }

    checkIfZero(mat);
}

void testColMatrixWriting(linalg::Matrix<double> mat) {
    std::vector<std::thread> col_threads;
    for (int i = 0; i < mat.cols(); i++) {
        col_threads.emplace_back([&mat, i]{
            for (int j = 0; j < mat.rows(); j++) {
                mat[j][i] = 0.0;
            }
        });
    }

    for (std::thread& t : col_threads) {
        t.join();
    }

    checkIfZero(mat);
}

TEST(ConcurrencyTests, RowMatrixWriting) {
    linalg::Matrix<double> mat = {
        {-1.0, 3.0, 1.0},
        {9.0, 1.0, 1.0},
        {2.0, 1.0, -1.0}
    };
    testRowMatrixWriting(std::move(mat));
    
    linalg::util::RandomScalarGen<double> gen;
    for (int i = 0; i < 10; i++) {
        testRowMatrixWriting(linalg::util::getRandomMatrix<double>(100, 100, gen));
    }
}

TEST(ConcurrencyTests, ColumnMatrixWriting) {
    linalg::Matrix<double> mat = {
        {-1.0, 3.0, 1.0},
        {9.0, 1.0, 1.0},
        {2.0, 1.0, -1.0}
    };
    testColMatrixWriting(std::move(mat));
    
    linalg::util::RandomScalarGen<double> gen;
    for (int i = 0; i < 10; i++) {
        testColMatrixWriting(linalg::util::getRandomMatrix<double>(100, 100, gen));
    }
}