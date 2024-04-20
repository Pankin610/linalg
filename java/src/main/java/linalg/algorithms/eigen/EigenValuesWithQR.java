package linalg.algorithms.eigen;

import linalg.matrix.*;
import linalg.algorithms.QRDecomposition;
import linalg.algorithms.eigen.EigenValuesComputer;
import java.util.Collection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

// This class is immutable
public class EigenValuesWithQR implements EigenValuesComputer {
  public EigenValuesWithQR(QRDecomposition decomposer, double eps, int max_iter) {
    decomposer_ = decomposer;
    eps_ = eps;
    max_iter_ = max_iter;
  }

  public EigenValuesWithQR(QRDecomposition decomposer) {
    decomposer_ = decomposer;
  }

  @Override
  public Collection<Double> GetEigenValues(Matrix mat) throws InterruptedException, ExecutionException {
    Matrix cur_mat = mat;
    int max_iter = max_iter_;
    // Until the matrix isn't converged we get its QR and assign it to R*Q
    while(max_iter != 0) {
      max_iter--;
      decomposer_.Decompose(cur_mat);
      Matrix new_mat = MatrixFactory.DenseMultiply(decomposer_.GetR(), 
                                                   decomposer_.GetQ());
      boolean changed = false;
      for (int i = 0; i < cur_mat.Rows() && !changed; i++) {
        for (int j = 0; j < cur_mat.Cols() && !changed; j++) {
          if (Math.abs(Math.abs(cur_mat.ValueAt(i, j)) - 
                       Math.abs(new_mat.ValueAt(i, j))) > eps_) {
            changed = true;
          }
        }
      }

      if (!changed) {
        break;
      }
      cur_mat = new_mat;
    }
    
    ArrayList<Double> eigen_values = new ArrayList<>();
    for (int i = 0; i < cur_mat.Cols(); i++) {
      eigen_values.add(cur_mat.ValueAt(i, i));
    }

    return eigen_values;
  }

  private QRDecomposition decomposer_;
  // -1 means no limit.
  private int max_iter_ = -1;
  double eps_ = 1e-9;
}
