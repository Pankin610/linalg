#include <cmath>

namespace linalg::util {

template<typename T = double>
double isNear(const T& a, const T& b, const T& tol) {
  return fabs(a - b) < tol;
}
  
}