package linalg.vector;

import linalg.vector.DenseVector;
import linalg.vector.VectorEntry;

public class DenseVectorBuilder extends VectorBuilder {
	public DenseVectorBuilder(int sz) {
		super(sz);
		values_ = new double[sz];
	}

	@Override
	protected void SetValueSafe(int index, double value) {
		values_[index] = value;
	}

	@Override
	protected double GetValueSafe(int index) {
		return values_[index];
	}

	@Override
	public Vector BuildVector() {
		return new DenseVector(values_);
	}

	double[] values_;
}


