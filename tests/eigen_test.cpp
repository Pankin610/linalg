#include <gtest/gtest.h>

#include "eigen.h"

TEST(DiagonalTest, EigenTests) {
  linalg::Matrix<double> mat = {
    {3.0, 0.0, 0.0},
    {0.0, 3.0, 0.0},
    {0.0, 0.0, 3.0}
  };

  EXPECT_NEAR(linalg::eigen::getBiggestEigenValue(mat), 3.0, 1e-11);
}