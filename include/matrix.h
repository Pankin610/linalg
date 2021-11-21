#pragma once

#include <stdexcept>
#include <vector>
#include <iostream>

#include "vector.h"

namespace linalg {

template<typename T>
class Matrix {
  std::vector<Vector<T>> _vectors;
  int _rows;
  int _cols;

 public:
  Matrix(int n, int m) : _vectors(n, Vector<T>(m)), _rows(n), _cols(m) {}
  Matrix(int n, int m, T def) : _vectors(n, Vector<T>(m, def)), _rows(n), _cols(m) {}

  Matrix(std::initializer_list<Vector<T>> init_list);
  Matrix(std::initializer_list<std::initializer_list<T>> init_list);

  Matrix(const Matrix& other) = default;
  Matrix(Matrix&& other) = default;

  Matrix& operator=(const Matrix& other) {
    _vectors = other._vectors;
    _rows = other._rows;
    _cols = other._cols;
  }

  int rows() const {
    return _rows;
  }
  int cols() const {
    return _cols;
  }

  Vector<T>& operator[](size_t row_ind) {
    return _vectors.at(row_ind);
  }
  const Vector<T>& operator[](size_t row_ind) const {
    return _vectors.at(row_ind);
  }

  Vector<T> multiply(const Vector<T>& vector) const; 

  Matrix<T> multiply(const Matrix<T>& mat) const; 

  bool isSquare() const {
    return rows() == cols();
  }
};

template<typename T>
Matrix<T>::Matrix(std::initializer_list<Vector<T>> init_list): _vectors(init_list) {
  _rows = _vectors.size();
  _cols = _vectors[0].size();
}

template<typename T>
Matrix<T>::Matrix(std::initializer_list<std::initializer_list<T>> init_list) {
  for (auto& vec_list : init_list) {
    _vectors.emplace_back(vec_list);
  }
  _rows = _vectors.size();
  _cols = _vectors[0].size();
}


template<typename T>
Vector<T> Matrix<T>::multiply(const Vector<T>& vector) const {
  if (vector.size() != cols()) {
    throw std::runtime_error("Vector size not equal to matrix column count.");
  }
  Vector<T> res(vector.size());
  for (int i = 0; i < cols(); i++) {
    res[i] = _vectors[i].multiply(vector);
  }
  return res;
}

template<typename T>
Matrix<T> Matrix<T>::multiply(const Matrix<T>& mat) const {
  if (cols() != mat.rows()) {
    throw std::runtime_error("Matrix sides don't coinside for the multiplication.");
  }
  Matrix<T> res(rows(), mat.cols());
  for (int i = 0; i < res.rows(); i++) {
    for (int j = 0; j < res.cols(); j++) {
      for (int k = 0; k < cols(); k++) {
        res[i][j] += (*this)[i][k] * mat[k][j];
      }
    }
  }
  return res;
}

template<typename T>
std::ostream& operator<<(std::ostream& st, const Matrix<T>& mat) {
    for (int i = 0; i < mat.rows(); i++) {
        st << mat[i] << '\n';
    }
    return st;
}

}