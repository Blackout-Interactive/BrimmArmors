package ema_08_.geom;

import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

public final class MatrixRTS {
	
	public static final MatrixRTS IDENTITY = new MatrixRTSBuilder().identify().build();
	
	public final float
		trX, trY, trZ,
		rtX, rtY, rtZ,
		scX, scY, scZ;
	
	public MatrixRTS(float translateX, float translateY, float translateZ,
			float rotateX, float rotateY, float rotateZ, float scaleX, float scaleY, float scaleZ) {
		this.trX = translateX; this.trY = translateY; this.trZ = translateZ;
		this.rtX = rotateX; this.rtY = rotateY; this.rtZ = rotateZ;
		this.scX = scaleX; this.scY = scaleY; this.scZ = scaleZ;
	}
	
	public void apply(@NonNull PoseStack poseStack) {
        poseStack.mulPose(Axis.XP.rotationDegrees(this.rtX));
        poseStack.mulPose(Axis.YP.rotationDegrees(this.rtY));
        poseStack.mulPose(Axis.ZP.rotationDegrees(this.rtZ));
        poseStack.scale(this.scX, this.scY, this.scZ);
        poseStack.translate(this.trX, this.trY, this.trZ);
    }

    @Override
    public boolean equals(Object o) {
    	if (o == null) return false;
        if (this == o) return true;
        if (o instanceof MatrixRTS that) {
        	return this.trX == that.trX && this.trY == that.trY && this.trZ == that.trZ &&
        			this.rtX == that.rtX && this.rtY == that.rtY && this.rtZ == that.rtZ &&
        			this.scX == that.scX && this.scY == that.scY && this.scZ == that.scZ;
        } else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.trX, this.trY, this.trZ, this.rtX, this.rtY, this.rtZ, this.scX, this.scY, this.scZ);
    }
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(getClass().getSimpleName());
    	sb.append("{");
    	sb.append("trX=").append(this.trX).append("; ");
    	sb.append("trY=").append(this.trY).append("; ");
    	sb.append("trZ=").append(this.trZ).append("; ");
    	sb.append("rtX=").append(this.rtX).append("; ");
    	sb.append("rtY=").append(this.rtY).append("; ");
    	sb.append("rtZ=").append(this.rtZ).append("; ");
    	sb.append("scX=").append(this.scX).append("; ");
    	sb.append("scY=").append(this.scY).append("; ");
    	sb.append("scZ=").append(this.scZ);
    	sb.append("}");
    	return sb.toString();
    }

}
