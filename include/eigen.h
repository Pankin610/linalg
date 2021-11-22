#include <exception>

#include "matrix.h"
#include "vector.h"
#include "rng.h"
#include "util.h"

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
  while(max_iter--) {
    auto new_vec = mat.multiply(vec);
    T lambda = new_vec[0] / vec[0];

    bool is_eigen = true;
    for (int i = 0; i < vec.size(); i++) {
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

}