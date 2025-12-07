package blackoutInteractive.ema_08_.rendering.geom;

import java.util.concurrent.ConcurrentHashMap;

import org.joml.Quaternionf;

public class RotQuaternionPool {
	
	private static final ConcurrentHashMap<Long, Quaternionf> CACHE_POOL = new ConcurrentHashMap<>();
	
	private static float roundf(float f) {
		return Math.round(f * 10000f) / 10000f;
	}
	
	private static long hash(float rrotX,  float rrotY, float rrotZ) {
		int hx = Float.floatToIntBits(rrotX);
		int hy = Float.floatToIntBits(rrotY);
		int hz = Float.floatToIntBits(rrotZ);
		long hash = 17L;
		hash = hash * 31L + hx;
		hash = hash * 31L + hy;
		hash = hash * 31L + hz;
		return hash;
	}
	
	public static boolean isCached(float rotX, float rotY, float rotZ) {
		return CACHE_POOL.get(hash(roundf(rotX), roundf(rotY), roundf(rotZ))) != null;
	}
	
	protected static Quaternionf retrieve(float rotX, float rotY, float rotZ) {
		float
			rrotX = roundf(rotX),
			rrotY = roundf(rotY),
			rrotZ = roundf(rotZ);
		return CACHE_POOL.computeIfAbsent(hash(rrotX, rrotY, rrotZ), (key)->computeIndependent0(rrotX, rrotY, rrotZ));
	}
	
	protected static Quaternionf computeIndependent0(float rotX, float rotY, float rotZ) {
		Quaternionf qy = new Quaternionf().rotationY((float)Math.toRadians(rotY));
		Quaternionf qx = new Quaternionf().rotationX((float)Math.toRadians(rotX));
		Quaternionf qz = new Quaternionf().rotationZ((float)Math.toRadians(rotZ));
		return new Quaternionf(qz).mul(qx).mul(qy);
	}
	
	public static void freeAll() {
		CACHE_POOL.clear();
	}

}
