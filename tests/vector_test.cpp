#include <gtest/gtest.h>
#include "vector.h"

TEST(VectorTest, BasicTests) {
    linalg::Vector<double> a(5, 0.0);
    EXPECT_EQ(a.size(), 5);
    for (int i = 0; i < 5; i++) {
        EXPECT_EQ(a[i], 0.0);
    }
}