// the linear algebra's Vector class
#include <iostream>
#include <vector>

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
    checkIndexBounds(ind, size());
    return _values[ind];
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