#pragma once

#include <vector>

#include "vector.h"

namespace linalg::orthogonal {

template<typename T>
std::vector<Vector<T>> getOrthonormalBasis(const std::vector<Vector<T>>& vectors) {
  if (vectors.empty()) {
    return {};
  }

  std::vector<Vector<T>> basis;
  basis.reserve(vectors.size());
  basis.push_back(vectors[0]);
  basis.back().normalize();
  for (int i = 1; i < vectors.size(); i++) {
    Vector<T> ort = vectors[i];
    for (const Vector<T>& other : basis) {
      ort -= other.getProjection(vectors[i]);
    }
    ort.normalize();
    basis.push_back(std::move(ort));
  }
  return basis;
}

}