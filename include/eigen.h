#pragma once

#include <exception>
#include <vector>

#include "matrix.h"
#include "vector.h"
#include "rng.h"
#include "util.h"
#include "qr_decomposition.h"

namespace linalg::eigen {

struct NoEigenValueException : public std::exception {
  const char* what() const throw() {
    return "No Eigen value was found";
  }
};

// performs the simple iteration method
// implemented only for floating point basic types
template<typename T>
T getBiggestEigenValue(
  const Matrix<T>& mat, 
  int max_iter = 100,
  T tol = 1e-12) throw(NoEigenValueException)
{
  if (!mat.isSquare()) {
    throw std::runtime_error("The matrix isn't square.");
  }

  util::RandomScalarGen<T> rng;

  Vector<T> vec = util::getRandomVector<T>(mat.rows(), rng);
  for (int i = 0; i < 5; i++) {
    vec = mat.multiply(vec).normalize();
  }

  while(max_iter--) {
    auto new_vec = mat.multiply(vec);
    T lambda;
    for (int i = 0; i < vec.size(); i++) {
      if (util::isNear(new_vec[i], 0.0, tol) || util::isNear(vec[i], 0.0, tol)) {
        continue;
      }
      lambda = new_vec[i] / vec[i];
      break;
    }

    bool is_eigen = true;
    for (int i = 0; i < vec.size(); i++) {
      if (util::isNear(new_vec[i], 0.0, tol) || util::isNear(vec[i], 0.0, tol)) {
        continue;
      }
      if (!util::isNear(vec[i] * lambda, new_vec[i], tol)) {
        is_eigen = false;
        break;
      }
    }
    if (is_eigen) {
      return lambda;
    }

    vec = std::move(new_vec);
    vec.normalize();
  }

  throw NoEigenValueException();
}

template<typename T>
std::vector<T> getEigenvalues(const Matrix<T>& mat, int max_iter = 100) {
  Matrix<T> a = mat;
  for (int i = 0; i < max_iter; i++) {
    GramianSchmidtQR<T> qr(a);
    a = qr.getR().multiply(qr.getQ());
  }

  std::vector<T> eigen_vals;
  for (int i = 0; i < a.cols(); i++) {
    eigen_vals.push_back(a[i][i]);
  }
  return eigen_vals;
}

}