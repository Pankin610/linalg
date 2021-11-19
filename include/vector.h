// the linear algebra's Vector class
#pragma once

#include <iostream>
#include <vector>
#include <algorithm>

namespace linalg {

template<typename T>
class Vector {
  std::vector<T> _values;

  void _checkVectorSize(const Vector<T>& other) const; 

 public:
  // TODO check the vector doesn't have length 0
  explicit Vector(std::vector<T> _values): _values(std::move(_values)) {}
  Vector(const Vector<T>& other) = default;
  Vector(Vector<T>&& other) = default;

  Vector(int n) : _values(n) {}
  Vector(int n, T def) : _values(n, def) {}

  Vector<T>& operator=(const Vector<T>& other) {
    _values = other._values;
    return *this;
  }

  T& operator[](size_t ind) {
    return _values.at(ind);
  }
  const T& operator[](size_t ind) const {
    checkIndexBounds(ind, size());
    return _values[ind];
  }

  size_t size() const {
    return _values.size();
  }

  T scalarMultiply(const Vector<T>& other) const; 

  Vector<T>& unapplyPermute(std::vector<int> p); 

  T getLength() const; 

  Vector<T>& normalize(); 

  Vector<T> getProjection(const Vector<T>& other) const; 

  friend Vector<T>& operator+=(Vector<T>& vec, const T& scalar);
  friend Vector<T>& operator-=(Vector<T>& vec, const T& scalar);
  friend Vector<T>& operator*=(Vector<T>& vec, const T& scalar);

  friend Vector<T> operator+(const Vector<T>& vec, const T& scalar);
  friend Vector<T> operator-(const Vector<T>& vec, const T& scalar);
  friend Vector<T> operator*(const Vector<T>& vec, const T& scalar);

  friend Vector<T>& operator+=(Vector<T>& vec, const Vector<T>& other);
  friend Vector<T>& operator-=(Vector<T>& vec, const Vector<T>& other);
  friend Vector<T>& operator*=(Vector<T>& vec, const Vector<T>& other);

  friend Vector<T> operator+(const Vector<T>& vec, const Vector<T>& other);
  friend Vector<T> operator-(const Vector<T>& vec, const Vector<T>& other);
  friend Vector<T> operator*(const Vector<T>& vec, const Vector<T>& other);
};

template<typename T>
T Vector<T>::scalarMultiply(const Vector<T>& other) const {
  _checkVectorSize(other);

  T ans = other._values[0] * _values[0];
  for (size_t i = 1; i < size(); i++) {
    ans += other._values[i] * _values[i];
  }
  return ans;
} 

template<typename T>
Vector<T>& Vector<T>::unapplyPermute(std::vector<int> p) {
  if (p.size() != size()) {
    throw std::runtime_error("Permutation size different from vector size.");
  }
  for (int i = 0; i < size(); i++) {
    while(p[i] != i) {
      std::swap(_values[p[i]], _values[p[p[i]]]);
      std::swap(p[i], p[p[i]]);
    }
  }
  return *this;
}

template<typename T>
T Vector<T>::getLength() const {
  T len = _values[0] * _values[0];
  for (int i = 1; i < size(); i++) {
    len += _values[i] * _values[i];
  }
  return sqrt(len);
}

template<typename T>
Vector<T>& Vector<T>::normalize() {
  T len = getLength();
  for (int i = 0; i < size(); i++) {
    _values[i] /= len;
  }
  return *this;
}

template<typename T>
Vector<T> Vector<T>::getProjection(const Vector<T>& other) const {
  _checkVectorSize(other);

  return (*this) * (scalarMultiply(other) / scalarMultiply(*this));
}

// Vector operators
template<typename T>
Vector<T>& operator+=(Vector<T>& vec, const T& scalar) {
  for (T& val : vec._values) {
    val += scalar;
  }
  return vec;
}

template<typename T>
Vector<T>& operator*=(Vector<T>& vec, const T& scalar) {
  for (T& val : vec._values) {
    val *= scalar;
  }
  return vec;
}

template<typename T>
Vector<T>& operator/=(Vector<T>& vec, const T& scalar) {
  for (T& val : vec._values) {
    val /= scalar;
  }
  return vec;
}

template<typename T>
Vector<T> operator+(const Vector<T>& vec, const T& scalar) {
  Vector<T> res = vec;
  res += scalar;
  return res;
}

template<typename T>
Vector<T> operator*(const Vector<T>& vec, const T& scalar) {
  Vector<T> res = vec;
  res *= scalar;
  return res;
}

template<typename T>
Vector<T> operator/(const Vector<T>& vec, const T& scalar) {
  Vector<T> res = vec;
  res /= scalar;
  return res;
}

template<typename T>
Vector<T>& operator+=(Vector<T>& vec, const Vector<T>& other) {
  vec._checkVectorSize(other);
  for (int i = 0; i < vec.size(); i++) {
    vec._values[i] += other[i];
  }
  return vec;
}

template<typename T>
Vector<T>& operator-=(Vector<T>& vec, const Vector<T>& other) {
  vec._checkVectorSize(other);
  for (int i = 0; i < vec.size(); i++) {
    vec._values[i] -= other[i];
  }
  return vec;
}

template<typename T>
Vector<T> operator+(const Vector<T>& vec, const Vector<T>& other) {
  Vector<T> res = vec;
  res += other;
  return res;
}

template<typename T>
Vector<T> operator-(const Vector<T>& vec, const Vector<T>& other) {
  Vector<T> res = vec;
  res -= other;
  return res;
}

template<typename T>
std::ostream& operator<<(std::ostream& st, const Vector<T>& v) {
  for (int i = 0; i < v.size(); i++) {
    st << v[i] << ' ';
  }
  return st;
}

}