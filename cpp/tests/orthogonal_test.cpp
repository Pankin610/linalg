#include <gtest/gtest.h>

#include "orthogonal.h"
#include "vector.h"
#include <vector>

TEST(OrthogonalTests, TwoVectorsTest) {
  linalg::Vector<double> a = {0.1, 0.9, 5.0};
  linalg::Vector<double> b = {0.3, 0.8, 12.0};

  auto orth = linalg::orthogonal::getOrthonormalBasis(std::vector<linalg::Vector<double>>({a, b}));
  for (auto& v : orth) {
    EXPECT_NEAR(v.getLength(), 1.0, 1e-15);
  }
  EXPECT_NEAR(orth[0].innerProduct(orth[1]), 0.0, 1e-15);
}

TEST(OrthogonalTests, BasicTest) {
  std::vector<linalg::Vector<double>> vectors = {
    {6.000, 8.000, 0.000},
    {10.000, 5.000, 12.000}
  };
  std::vector<linalg::Vector<double>> expected = {
    {0.6000000000, 0.8000000000, 0.0000000000},
    {0.3076923077, -0.2307692308, 0.9230769231}
  };

  auto basis = linalg::orthogonal::getOrthonormalBasis(vectors);
  for (int i = 0; i < 2; i++) {
    for (int j = 0; j < 3; j++) {
      EXPECT_NEAR(basis[i][j], expected[i][j], 1e-6);
    }
  }
}