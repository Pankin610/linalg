#pragma once

#include <random>
#include <vector>

namespace linalg::util {
  
template<typename T = double>
class RandomScalarGen
{
  std::random_device _rd{};
  std::mt19937 _gen{_rd()};
  std::uniform_real_distribution<T> _dist;

 public:
  RandomScalarGen() {
    set(0.0, 1.0);
  }
        
  RandomScalarGen(T low, T high) {
    set(low, high);
  }

  // Update the distribution parameters for half-open range [low, high).
  void set(T low, T high) {
    typename std::uniform_real_distribution<T>::param_type param(low, high);
    _dist.param(param);
  }

  T get() {
    return _dist(_gen);
  }
};

template<typename T = double>
Vector<T> getRandomVector(int n, RandomScalarGen<T>& gen) {
  Vector<T> vec(n);
  for (int i = 0; i < n; i++) {
    vec[i] = gen.get();
  }
  return vec;
}

template<typename T = double>
Matrix<T> getRandomMatrix(int n, int m, RandomScalarGen<T>& gen) {
  std::vector<Vector<T>> rows;
  rows.reserve(n);

  for (int i = 0; i < n; i++) {
    rows.push_back(getRandomVector(m, gen));
  }

  return Matrix<T>(rows);
}

}