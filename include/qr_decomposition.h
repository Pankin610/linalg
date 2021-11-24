#pragma once

#include "matrix.h"
#include "vector.h"
#include "orthogonal.h"

namespace linalg {

template<typename T>
class QRDecomposition {
  Matrix<T> _Q_T;
  Matrix<T> _R;
 public:
  QRDecomposition(const Matrix<T>& mat);

  Matrix<T> getQ() { return _Q_T.transposed(); }
  Matrix<T> getR() { return _R; }
};

template<typename T>
QRDecomposition<T>::QRDecomposition(const Matrix<T>& mat) :
  _Q_T(orthogonal::getOrthonormalBasis(mat.getColVectors())),
  _R(_Q_T.multiply(mat)) {}

}