package linalg.algorithms;

import linalg.vector.Vector;
import linalg.matrix.Matrix;
import linalg.matrix.DenseMatrix;
import linalg.matrix.DenseMatrixBuilder;


public class LUDecomposition{

  private Matrix L = null;      
  private Matrix U = null;  
  private Matrix Mat = null;

  public LUDecomposition (Matrix Mat){
    if(Mat.Cols() != Mat.Rows()){
      throw new IllegalArgumentException("Wrong matrix dimensions");
    }
    this.Mat = Mat;
  };

  public void Decompose(){
    ConstructionLU();
  }

  private void ConstructionLU () {
    final int n = Mat.Rows();

    DenseMatrixBuilder LComponent = new DenseMatrixBuilder(n,n);
    DenseMatrixBuilder UComponent = new DenseMatrixBuilder(n,n);

    //Doolittle's Method for LU Decompositions

    for(int i=0;i<n;i++){
      //Calculating U
      for(int k = i;k < n;k++){
        int sum= 0;
        for(int j = 0;j < i;j++){
          if(Thread.interrupted()){
            return;
          }
          sum+=(LComponent.GetValue(i,j)*UComponent.GetValue(j,k));
        }
        UComponent.SetValue(i,k, Mat.ValueAt(i,k) - sum);
      }
      //Calculating L
      for(int k = i;k < n;k++){
        if(i == k){ 
          LComponent.SetValue(i,i,1);
        }else{
          int sum= 0;
          for(int j = 0;j < i;j++){
            if(Thread.interrupted()){
              return;
            }
            sum+=(LComponent.GetValue(k,j)*UComponent.GetValue(j,i));
          }
          LComponent.SetValue(k,i, (Mat.ValueAt(k,i) - sum)/UComponent.GetValue(i,i));
        }
      }
    }

    L = LComponent.BuildMatrix();
    U = UComponent.BuildMatrix();
  }

  public Matrix L(){
    return L;
  }

  public Matrix U(){
    return U;
  }

  public Matrix Mat(){
    return Mat;
  }

  public double Determinant(){
    double res = 1;
    final int n = Mat.Rows();
        
    for(int i = 0;i < n;i++){
      res*=L.ValueAt(i,i);
    }
    for(int i = 0;i < n;i++){
      res*=U.ValueAt(i,i);
    }
    return res;
  }

  //TODO: Implement function that solves AX = B
}
