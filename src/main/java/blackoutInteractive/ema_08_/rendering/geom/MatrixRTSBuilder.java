package blackoutInteractive.ema_08_.rendering.geom;

import blackoutInteractive.ema_08_.misc.IBuilder;

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
    
    public MatrixRTSBuilder setTranslateX(float value) {
    	this.translate[0] = value;
    	return this;
    }
    
    public MatrixRTSBuilder setTranslateY(float value) {
    	this.translate[1] = value;
    	return this;
    }
    
    public MatrixRTSBuilder setTranslateZ(float value) {
    	this.translate[2] = value;
    	return this;
    }
    
    public MatrixRTSBuilder setRotateX(float value) {
    	this.rotate[0] = value;
    	return this;
    }
    
    public MatrixRTSBuilder setRotateY(float value) {
    	this.rotate[1] = value;
    	return this;
    }
    
    public MatrixRTSBuilder setRotateZ(float value) {
    	this.rotate[2] = value;
    	return this;
    }
    
    public MatrixRTSBuilder setScaleX(float value) {
    	this.scale[0] = value;
    	return this;
    }
    
    public MatrixRTSBuilder setScaleY(float value) {
    	this.scale[1] = value;
    	return this;
    }
    
    public MatrixRTSBuilder setScaleZ(float value) {
    	this.scale[2] = value;
    	return this;
    }
    
    @Override
    public MatrixRTS build() {
    	return MatrixRTS.getMatrix(this.translate[0], this.translate[1], this.translate[2],
    			this.rotate[0], this.rotate[1], this.rotate[2],
    			this.scale[0], this.scale[1], this.scale[2]);
    }
    
}

