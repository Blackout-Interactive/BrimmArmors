package ema_08_.geom.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

public final class RTSMatricesCompound {
	
	public static final RTSMatricesCompound EMPTY = new RTSMatricesCompound(new HashMap<>());
	
	public static final String
		key_armor_render = "ARMOR",
		key_gui_render = "GUI",
		key_workbench_render = "WORKBENCH";
	
	private final Map<String, MatrixRTS> matrices;
	
	public RTSMatricesCompound(Map<String, MatrixRTS> matrices) {
		matrices.entrySet().forEach((e)->{
			validateKey(e.getKey());
			if (e.getValue() == null) throw new IllegalArgumentException("Cannot map a null matrix");
		});
		this.matrices = Collections.unmodifiableMap(new HashMap<>(matrices));
	}
	
	private static String validateKey(String key) {
		if (key == null) throw new IllegalArgumentException("Invalid matrices compound key");
		return key;
	}
	
	public boolean has(String key) {
		return this.matrices.get(validateKey(key)) != null;
	}
	
	public MatrixRTS get(String key) {
		MatrixRTS matrix = this.matrices.get(validateKey(key));
		if (matrix == null) throw new IllegalArgumentException("No matrix under key "+key);
		return matrix;
	}
	
	public MatrixRTS getOrIdentity(String key) {
		MatrixRTS matrix = this.matrices.get(validateKey(key));
		return matrix == null ? MatrixRTS.IDENTITY : matrix;
	}
	
	public void applyIfPresent(String key, PoseStack poseStack) {
		MatrixRTS matrix = this.matrices.get(validateKey(key));
		if (matrix != null) matrix.apply(poseStack);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
        if (this == o) return true;
        if (o instanceof RTSMatricesCompound that) return this.matrices.equals(that.matrices);
        else return false;
	}
	
	@Override
	public int hashCode() {
		return 17 * this.matrices.hashCode();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+"{"+String.join("; ", this.matrices.keySet())+"}";
	}

}
