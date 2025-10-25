package ema_08_.rendering.geom;

import java.util.HashMap;
import java.util.Objects;

import ema_08_.misc.IBuilder;

public final class RTSMatricesCompoundBuilder implements IBuilder<RTSMatricesCompound> {
	
	private final HashMap<String, MatrixRTS> matrices = new HashMap<>();
	
	private static String validateKey(String key) {
		if (key == null) throw new IllegalArgumentException("Invalid matrices compound key");
		return key;
	}
	
	public boolean has(String key) {
		return this.matrices.get(validateKey(key)) != null;
	}
	
	public RTSMatricesCompoundBuilder set(String key, MatrixRTS matrix, boolean overwrite) {
		MatrixRTS oldMatrix = this.matrices.get(validateKey(key));
		if (oldMatrix != null && !overwrite) throw new IllegalStateException("Matrix already present under key "+key);
		this.matrices.put(key, Objects.requireNonNull(matrix, "Cannot add a null matrix"));
		return this;
	}
	
	public RTSMatricesCompoundBuilder set(String key, MatrixRTS matrix) {
		return set(key, matrix, true);
	}
	
	public RTSMatricesCompoundBuilder set(String key, MatrixRTSBuilder matrixB) {
		return set(key, matrixB.build());
	}

	@Override
	public RTSMatricesCompound build() {
		return new RTSMatricesCompound(this.matrices);
	}

}
