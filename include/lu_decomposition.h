#pragma once

#include "matrix.h"
#include "vector.h"
#include <vector>
#include <algorithm>
#include <cassert>

namespace linalg {

template<typename T>
class LUDecomposition {
  Matrix<T> _L;
  Matrix<T> _U;
  std::vector<int> _p;

  void getPivotingPermutation();

  Vector<T> solveLowerTriangle(
    const Matrix<T>& mat, 
    const Vector<T>& res_vec) const;

  Vector<T> solveUpperTriangle(
    const Matrix<T>& mat, 
    const Vector<T>& res_vec) const;

 public:
  LUDecomposition(Matrix<T> mat, T one);

  Matrix<T> getL() const { return _L; }
  Matrix<T> getU() const { return _U; }
  std::vector<int> getPermutation() const { return _p; }

  Vector<T> solveForVector(const Vector<T>& res_vec) const {
    return solveUpperTriangle(_U, solveLowerTriangle(_L, res_vec)).unapplyPermute(_p);
  }
};

template<typename T>
void LUDecomposition<T>::getPivotingPermutation() {
  auto temp = _U;
  int n = temp.rows();
  _p.resize(n);
  for (int i = 0; i < n; i++) {
    _p[i] = i;
  }

  for (int row = 0; row < n - 1; row++) {
    int best_row = row;
    for (int next_row = row + 1; next_row < n; next_row++) {
      if (fabs(temp[_p[next_row]][row]) > fabs(temp[_p[best_row]][row])) {
        best_row = next_row;
      }
    }
    std::swap(_p[best_row], _p[row]);

    for (int next_row = row + 1; next_row < n; next_row++) {
      T coef = temp[_p[next_row]][row] / temp[_p[row]][row];
      temp[_p[next_row]] -= temp[_p[row]] * coef;
    }
  }
}

template<typename T>
Vector<T> LUDecomposition<T>::solveLowerTriangle(
  const Matrix<T>& mat, 
  const Vector<T>& res_vec) const
{
  assert(mat.rows() == mat.cols() && mat.rows() == res_vec.size());
  Vector<T> res(res_vec.size());
  for (int i = 0; i < res.size(); i++) {
    res[_p[i]] = res_vec[_p[i]];
    for (int j = 0; j < i; j++) {
      res[_p[i]] -= mat[_p[i]][j] * res[_p[j]];
    }  
    res[_p[i]] /= mat[_p[i]][i];
  }
  return res;
}

template<typename T>
Vector<T> LUDecomposition<T>::solveUpperTriangle(
  const Matrix<T>& mat, 
  const Vector<T>& res_vec) const
{
  assert(mat.rows() == mat.cols() && mat.rows() == res_vec.size());
  Vector<T> res(res_vec.size());
  for (int i = res.size() - 1; i >= 0; i--) {
    res[_p[i]] = res_vec[_p[i]];
    for (int j = res.size() - 1; j > i; j--) {
      res[_p[i]] -= mat[_p[i]][j] * res[_p[j]];
    }  
    res[_p[i]] /= mat[_p[i]][i];
  }
  return res;
}

template<typename T>
LUDecomposition<T>::LUDecomposition(Matrix<T> mat, T one):
  _L(mat.rows(), mat.cols()),
  _U(std::move(mat))
{
  if (!_U.isSquare()) {
    throw new std::runtime_error("Only square matrices have an LU decomposition.");
  }
  getPivotingPermutation();

  int n = _U.rows();
  for (int i = 0; i < n; i++) {
    _L[_p[i]][i] = one;
  }
  for (int row = 0; row < n - 1; row++) {
    for (int next_row = row + 1; next_row < n; next_row++) {
      T coef = _U[_p[next_row]][row] / _U[_p[row]][row];
      _U[_p[next_row]] -= _U[_p[row]] * coef;
      _L[_p[next_row]][row] = coef;
    }
  }
}

}