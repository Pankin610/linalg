#pragma once

#include "matrix.h"
#include "vector.h"
#include "orthogonal.h"
#include "util.h"

#include <mutex>
#include <thread>
#include <vector>
#include <iostream>
#include <future>
#include <omp.h>

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
  
 protected:
  struct GivensRotation {
    // the 2x2 rotation matrix
    Matrix<T> mat;
    // the cell supposedly zeroed out by the rotation
    int x;
    int y;

    GivensRotation(const Matrix<T>& inp_mat, int i, int j);

    void apply(Matrix<T>& m) const;
    void transpose() { std::swap(mat[0][1], mat[1][0]); }
  };

  void applyRotationOnR(GivensRotation& rotation) {
    rotation.transpose();
    rotation.apply(_R);
    rotation.transpose();
  }

  void applyRotationOnQ(const GivensRotation& rotation) {
    rotation.apply(_Q);
  }

  GivensRotationsQR(const Matrix<T>& mat) {
    _R = mat;
    _Q = Matrix<T>::identity(mat.rows(), mat.rows());
  }

  Matrix<T>& getCurR() { return _R; }

 public:
  Matrix<T> getQ() override { return _Q; }
  Matrix<T> getR() override { return _R; }  
};

template<typename T>
class SimpleGivensRotations : public GivensRotationsQR<T> {
 public:
  SimpleGivensRotations(const Matrix<T>& mat);
};

template<typename T>
class ParallelGivensRotations : public GivensRotationsQR<T> {
 public:
  ParallelGivensRotations(const Matrix<T>& mat);
};

template<typename T>
class OpenMPGivensRotations : public GivensRotationsQR<T> {
 public:
  OpenMPGivensRotations(const Matrix<T>& mat);
};

template<typename T>
GramianSchmidtQR<T>::GramianSchmidtQR(const Matrix<T>& mat) :
  _Q_T(orthogonal::getOrthonormalBasis(mat.getColVectors())),
  _R(_Q_T.multiply(mat)) {}

template<typename T>
GivensRotationsQR<T>::GivensRotation::GivensRotation(
  const Matrix<T>& inp_mat,
  int i,
  int j) : mat(2, 2, 0.0), x(i), y(j)
{
  double a = inp_mat[i - 1][j];
  double b = inp_mat[i][j];
  double r = sqrt(a * a + b * b);
  double c = a / r;
  double s = -b / r;

  mat[1][1] = c;
  mat[0][0] = c;
  mat[1][0] = -s;
  mat[0][1] = -mat[1][0];
}

template<typename T>
void GivensRotationsQR<T>::GivensRotation::apply(Matrix<T>& m) const {
  // the rotation only affects the entry's row and the row coming before it
  for (int j = 0; j < m.cols(); j++) {
    Vector<T> col_vec(2);
    col_vec[0] = m[x - 1][j];
    col_vec[1] = m[x][j];

    col_vec = mat.multiply(col_vec);

    m[x - 1][j] = col_vec[0];
    m[x][j] = col_vec[1];
  }
}

template<typename T>
SimpleGivensRotations<T>::SimpleGivensRotations(const Matrix<T>& mat) : 
  GivensRotationsQR<T>(mat) 
{
  typedef typename GivensRotationsQR<T>::GivensRotation GivensRotation;
  std::vector<GivensRotation> rotation_list;

  for (int j = 0; j < mat.cols(); j++) {
    Matrix<T>& R = this->getCurR();
    for (int i = R.rows() - 1; i > j; i--) {
      if (util::isNear(R[i][j], 0.0, 1e-14)) {
        continue;
      }

      auto rotation = GivensRotation(R, i, j);
      this->applyRotationOnR(rotation);
      rotation_list.emplace_back(std::move(rotation));
    }
  }
  
  std::reverse(rotation_list.begin(), rotation_list.end());
  for (auto& rot : rotation_list) {
    this->applyRotationOnQ(rot);
  }
}

template<typename T>
ParallelGivensRotations<T>::ParallelGivensRotations(const Matrix<T>& mat) : 
  GivensRotationsQR<T>(mat) 
{
  typedef typename GivensRotationsQR<T>::GivensRotation GivensRotation;
  std::vector<GivensRotation> rotation_list;
  Matrix<T>& R = this->getCurR();
  
  std::vector<std::vector<int>> thread_ind(R.rows(), std::vector<int>(R.cols()));
  int counter = 0;
  for (int j = 0; j < mat.cols(); j++) {
    for (int i = R.rows() - 1; i > j; i--) {
      thread_ind[i][j] = counter++;
    }
  }
  std::vector<std::thread> th;
  std::vector<std::promise<void>> promises;
  std::vector<std::shared_future<void>> futures;
  th.reserve(counter);
  promises.reserve(counter);
  futures.reserve(counter);
  for (int i = 0; i < counter; i++) {
    promises.emplace_back();
    futures.emplace_back(promises.back().get_future());
  }

  std::mutex rotation_list_mutex;
  for (int j = 0; j < mat.cols(); j++) {
    th.emplace_back([&, j](){
      for (int i = R.rows() - 1; i > j; i--) {
        if (j > 0) {
          futures[thread_ind[i - 1][j - 1]].wait();
        }

        if (util::isNear(R[i][j], 0.0, 1e-14)) {
          return;
        }

        auto rotation = GivensRotation(R, i, j);
        this->applyRotationOnR(rotation);
        std::unique_lock list_lock(rotation_list_mutex);
        rotation_list.emplace_back(std::move(rotation));

        promises[thread_ind[i][j]].set_value();
      }
    });
  }

  for (auto& t : th) {
    if (t.joinable()) t.join();
  }

  std::reverse(rotation_list.begin(), rotation_list.end());
  for (auto& rot : rotation_list) {
    this->applyRotationOnQ(rot);
  }
}

template<typename T>
OpenMPGivensRotations<T>::OpenMPGivensRotations(const Matrix<T>& mat) : 
  GivensRotationsQR<T>(mat) 
{
  typedef typename GivensRotationsQR<T>::GivensRotation GivensRotation;
  std::vector<GivensRotation> rotation_list;
  Matrix<T>& R = this->getCurR();

  // Last unprocessed row for each column
  std::vector<int> cur_row(mat.cols(), R.rows() - 1);
  // How many columns are being worked on
  std::vector<int> in_process = {0};
  // Cells per one run.
  int block_size = 4;

  auto process_cell = [&](int i, int j){
    if (util::isNear(R[i][j], 0.0, 1e-14)) {
      return;
    }

    auto rotation = GivensRotation(R, i, j);
    this->applyRotationOnR(rotation);
        
    #pragma omp critical
      rotation_list.emplace_back(std::move(rotation));
  };

  auto move_cols = [&](int block) {
    #pragma omp parallel for num_threads(8)
    for (int col : in_process) {
      int target = cur_row[col] - block;
      for (; cur_row[col] > target && cur_row[col] > col; cur_row[col]--) {
        process_cell(cur_row[col], col);
      }
    }
  };

  while(!in_process.empty()) {
    move_cols(block_size);
    if (in_process.back() + 1 < mat.cols()) {
      move_cols(1);
      in_process.push_back(in_process.back() + 1);
    }
    for (int i = 0; i < in_process.size(); i++) {
      int col = in_process[i];
      if (cur_row[col] == col) {
        in_process.erase(in_process.begin() + i);
        i--;
      }
    }
  }

  std::reverse(rotation_list.begin(), rotation_list.end());
  for (auto& rot : rotation_list) {
    this->applyRotationOnQ(rot);
  }
}


}