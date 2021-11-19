#include <gtest/gtest.h>
#include <exception>
#include "vector.h"

TEST(VectorSize, VectorFuncs) {
    linalg::Vector<double> a(5, 0.0);
    EXPECT_EQ(a.size(), 5);
    for (int i = 0; i < 5; i++) {
        EXPECT_EQ(a[i], 0.0);
    }
}

TEST(WrongVectorMult, VectorFuncs) {
    linalg::Vector<double> a(5, 0.0);
    linalg::Vector<double> b(6, 0.0);
    EXPECT_THROW({
        auto c = a.scalarMultiply(b);
    }, std::runtime_error);
}