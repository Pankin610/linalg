package linalg.algorithms.eigen;

// This class is immutable
class EifenValuesWithQR implements EigenValuesComputer {

  public EigenValuesWithQr(QRDecomposition decomposer, double eps) {
    decomposer_ = decomposer;
    eps_ = eps;
  }

  public EigenValuesWithQr(QRDecomposition decomposer) {
    decomposer_ = decomposer;
  }

  // TODO: implement once QRDecompositionBuilder is shipped
  @Override
  public Collection<Double> GetEigenValues(Matrix mat, int max_iter) {
    Matrix cur_mat = mat;
    while(max_iter > 0) {
    }
  }

  private QRDecomposition decomposer_;
  // -1 means no limit.
  private int max_num_iter_ = -1;
  double eps_ = 1e-9;
}
