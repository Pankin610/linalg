#include <gtest/gtest.h>

#include <iostream>

#include "eigen.h"

TEST(EigenTests, DiagonalTest) {
  linalg::Matrix<double> mat = {
    {3.0, 0.0, 0.0},
    {0.0, 3.0, 0.0},
    {0.0, 0.0, 3.0}
  };

  EXPECT_NEAR(linalg::eigen::getBiggestEigenValue(mat), 3.0, 1e-11);
}

TEST(EigenTests, EigenValsTests) {
  linalg::Matrix<double> mat = {
    {4.0, 1.0},
    {6.0, 3.0},
  };

  auto eigs = linalg::eigen::getEigenvalues(mat);
  EXPECT_NEAR(eigs[0], 6.0, 1e-11);
  EXPECT_NEAR(eigs[1], 1.0, 1e-11);
}