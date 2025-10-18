package ema_08_.geom;

import ema_08_.misc.IBuilder;

public final class MatrixRTSBuilder implements IBuilder<MatrixRTS> {
	
    private final float[] translate = new float[3];
    private final float[] rotate = new float[3];
    private final float[] scale = new float[3];
    
    private static void setValues(float[] arr, float v0, float v1, float v2) {
    	arr[0] = v0; arr[1] = v1; arr[2] = v2;
    }

    public MatrixRTSBuilder identify() {
    	setValues(this.translate, 0, 0, 0);
    	setValues(this.rotate, 0, 0, 0);
    	setValues(this.scale, 1, 1, 1);
        return this;
    }

    public MatrixRTSBuilder setTranslate(float x, float y, float z) {
    	setValues(this.translate, x, y, z);
        return this;
    }

    public MatrixRTSBuilder setRotate(float x, float y, float z) {
    	setValues(this.rotate, x, y, z);
        return this;
    }

    public MatrixRTSBuilder setScale(float x, float y, float z) {
    	setValues(this.scale, x, y, z);
        return this;
    }
    
    @Override
    public MatrixRTS build() {
    	return new MatrixRTS(
    			this.translate[0], this.translate[1], this.translate[2],
    			this.rotate[0], this.rotate[1], this.rotate[2],
    			this.scale[0], this.scale[1], this.scale[2]
    		);
    }
    
}

