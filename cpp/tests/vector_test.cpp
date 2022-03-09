#include <gtest/gtest.h>
#include <exception>
#include "vector.h"

TEST(VectorSize, VectorTests) {
    linalg::Vector<double> a(5, 0.0);
    EXPECT_EQ(a.size(), 5);
    for (int i = 0; i < 5; i++) {
        EXPECT_EQ(a[i], 0.0);
    }
}

TEST(WrongVectorMult, VectorTests) {
    linalg::Vector<double> a(5, 0.0);
    linalg::Vector<double> b(6, 0.0);
    EXPECT_THROW({
        auto c = a.innerProduct(b);
    }, std::runtime_error);
}

TEST(VectorOperators, VectorTests) {
    linalg::Vector<double> a = {0.1, 0.2, 0.3};
    linalg::Vector<double> b = a;
    b *= 3.0;
    for (int i = 0; i < 3; i++) {
        EXPECT_DOUBLE_EQ(a[i] * 3.0, b[i]);
    }

    b = linalg::Vector<double>(10, 5.0);
    EXPECT_THROW({
        auto c = b + a;
    }, std::runtime_error);
}

TEST(VectorLength, VectorTests) {
    linalg::Vector<double> v = {0.5, 0.6, 0.3};
    EXPECT_DOUBLE_EQ(v.getLength(), sqrt(v.innerProduct(v)));
}

TEST(VectorNorm, VectorTests) {
    linalg::Vector<double> big_v = {1000.0, 999.0, 123.0};
    linalg::Vector<double> small_v = big_v / 1000.0;

    EXPECT_DOUBLE_EQ(big_v.normalize().getLength(), 1.0);
    EXPECT_DOUBLE_EQ(small_v.normalize().getLength(), 1.0);
}