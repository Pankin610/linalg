#include <gtest/gtest.h>

#include "qr_decomposition.h"
#include "matrix.h"
#include "rng.h"

#include <exception>
#include <iostream>
#include <string>
#include <omp.h>


void checkQR(
  linalg::QRDecomposition<double>* decomp, 
  const linalg::Matrix<double>& mat)
{
  auto R = decomp->getR();
  for (int i = 0; i < R.rows(); i++) {
    for (int j = 0; j < std::min(i, R.cols()); j++) {
      EXPECT_NEAR(R[i][j], 0.0, 1e-15);
    }
  }

  auto Q = decomp->getQ();
  auto I = Q.multiply(Q.transposed());
  for (int i = 0; i < I.rows(); i++) {
    for (int j = 0; j < I.cols(); j++) {
      if (i == j) {
        EXPECT_NEAR(I[i][j], 1.0, 1e-15);
      }
      else {
        EXPECT_NEAR(I[i][j], 0.0, 1e-15);
      }
    }
  }

  auto exp_A = Q.multiply(R);
  for (int i = 0; i < mat.rows(); i++) {
    for (int j = 0; j < mat.cols(); j++) {
      EXPECT_NEAR(exp_A[i][j], mat[i][j], 1e-15);
    }
  }
}

TEST(QR_Tests, BasicTest) {
  linalg::Matrix<double> mat = {
    {-1.0, 3.0, 1.0},
    {0.0, 1.0, 1.0},
    {2.0, 1.0, -1.0}
  };
  linalg::GramianSchmidtQR<double> decomp(mat);
  checkQR(&decomp, mat);
}

// an example from a paper: 
// https://www.math.usm.edu/lambers/mat610/sum10/lecture9.pdf
TEST(QR_Tests, SimpleGivensTest) {
  linalg::Matrix<double> mat = {
    {0.8147, 0.0975, 0.1576},
    {0.9058, 0.2785, 0.9706},
    {0.1270, 0.5469, 0.9572},
    {0.9134, 0.9575, 0.4854},
    {0.6324, 0.9649, 0.8003}      
  };
  linalg::SimpleGivensRotations<double> decomp(mat);
  checkQR(&decomp, mat);
}

TEST(QR_Tests, SparseGivensTest) {
  linalg::Matrix<double> mat = {
    {0.8147, 0.0975, 0.1576},
    {0.9058, 0.2785, 0.9706},
    {0.0,    0.5469, 0.9572},
    {0.0,    0.0,    0.4854},
    {0.0,    0.9649, 0.0}      
  };
  linalg::SimpleGivensRotations<double> decomp(mat);
  checkQR(&decomp, mat);
}

TEST(QR_Tests, ParallelGivensTest) {
  linalg::Matrix<double> mat = {
    {0.8147, 0.0975, 0.1576},
    {0.9058, 0.2785, 0.9706},
    {0.1270, 0.5469, 0.9572},
    {0.9134, 0.9575, 0.4854},
    {0.6324, 0.9649, 0.8003}
  };

  for (int i = 0; i < 100; i++) {
    linalg::ParallelGivensRotations<double> decomp(mat);
    checkQR(&decomp, mat);
  }
}

TEST(QR_Tests, OMPGivensTest) {
  linalg::Matrix<double> mat = {
    {0.8147, 0.0975, 0.1576},
    {0.9058, 0.2785, 0.9706},
    {0.1270, 0.5469, 0.9572},
    {0.9134, 0.9575, 0.4854},
    {0.6324, 0.9649, 0.8003}
  };

  for (int i = 0; i < 100; i++) {
    linalg::OpenMPGivensRotations<double> decomp(mat);
    checkQR(&decomp, mat);
  }
}

TEST(QR_Tests, BigGivensTest) {
  linalg::util::RandomScalarGen<double> gen;
  auto mat = linalg::util::getRandomMatrix<double>(200, 200, gen);

  linalg::SimpleGivensRotations<double> decomp(mat);
}

TEST(QR_Tests, BigParallelGivensTest) {
  linalg::util::RandomScalarGen<double> gen;
  auto mat = linalg::util::getRandomMatrix<double>(200, 200, gen);

  linalg::ParallelGivensRotations<double> decomp(mat);
}

TEST(QR_Tests, BigOpenMPGivensTest) {
  linalg::util::RandomScalarGen<double> gen;
  auto mat = linalg::util::getRandomMatrix<double>(200, 200, gen);

  linalg::OpenMPGivensRotations<double> decomp(mat);
}