#pragma once

#include "matrix.h"
#include "vector.h"
#include "orthogonal.h"
#include "util.h"

namespace linalg {

template<typename T>
class QRDecomposition {
 protected:
  QRDecomposition() {}
 public:
  virtual Matrix<T> getQ() {}; 
  virtual Matrix<T> getR() {};
};

template<typename T>
class GramianSchmidtQR : public QRDecomposition<T> {
  Matrix<T> _Q_T;
  Matrix<T> _R;
 public:
  GramianSchmidtQR(const Matrix<T>& mat);

  Matrix<T> getQ() override { return _Q_T.transposed(); }
  Matrix<T> getR() override { return _R; }
};

template<typename T>
class GivensRotationsQR : public QRDecomposition<T> {
  Matrix<T> _Q;
  Matrix<T> _R;
 public:
  static Matrix<T> getGivensRotation(const Matrix<T>& mat, int i, int j);
 public:
  GivensRotationsQR(const Matrix<T>& mat);

  Matrix<T> getQ() override { return _Q; }
  Matrix<T> getR() override { return _R; }
};

template<typename T>
GramianSchmidtQR<T>::GramianSchmidtQR(const Matrix<T>& mat) :
  _Q_T(orthogonal::getOrthonormalBasis(mat.getColVectors())),
  _R(_Q_T.multiply(mat)) {}

template<typename T>
Matrix<T> GivensRotationsQR<T>::getGivensRotation(
  const Matrix<T>& mat,
  int i,
  int j
) {
  double a = mat[i - 1][j];
  double b = mat[i][j];
  double r = sqrt(a * a + b * b);
  double c = a / r;
  double s = -b / r;

  Matrix<T> rotation = Matrix<T>::identity(mat.rows(), mat.rows());
  rotation[i][i] = c;
  rotation[i - 1][i - 1] = c;
  rotation[i][i - 1] = -s;
  rotation[i - 1][i] = -rotation[i][i - 1];

  return rotation;
}

template<typename T>
GivensRotationsQR<T>::GivensRotationsQR(const Matrix<T>& mat) {
  _R = mat;
  _Q = Matrix<T>::identity(mat.rows(), mat.rows());

  for (int j = 0; j < mat.cols(); j++) {
    for (int i = _R.cols() - 1; i > j; i--) {
      if (util::isNear(_R[i][j], 0.0, 1e-14)) {
        continue;
      }

      auto rotation = getGivensRotation(_R, i, j);
      _Q = _Q.multiply(rotation);
      _R = rotation.transposed().multiply(_R);
    }
  }
}

}