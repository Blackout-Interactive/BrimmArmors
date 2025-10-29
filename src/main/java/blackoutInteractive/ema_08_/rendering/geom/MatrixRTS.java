package blackoutInteractive.ema_08_.rendering.geom;

import java.util.Objects;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;

public abstract class MatrixRTS {
	
	public static final MatrixRTS IDENTITY = new IdentityMatrixRTS();
	
	public static MatrixRTS getMatrix(float translateX, float translateY, float translateZ,
    			float rotateX, float rotateY, float rotateZ, float scaleX, float scaleY, float scaleZ) {
		boolean
			translates = (translateX != 0 || translateY != 0 || translateZ != 0),
			rotates = (rotateX != 0 || rotateY != 0 || rotateZ != 0),
			scales = (scaleX != 1 || scaleY != 1 || scaleZ != 1);
		if (!translates && !rotates && !scales)
			return IDENTITY;
		else if (translates && rotates && scales)
			return new CompleteMatrixRTS(translateX, translateY, translateZ, rotateX, rotateY, rotateZ, scaleX, scaleY, scaleZ);
		else if (translates && !rotates && !scales)
			return new TranslOnlyMatrixRTS(translateX, translateY, translateZ);
		else if (rotates && !translates && !scales)
			return new RotOnlyMatrixRTS(rotateX, rotateY, rotateZ);
		else if (scales && !translates && !rotates)
			return new ScalOnlyMatrixRTS(scaleX, scaleY, scaleZ);
		else if (!translates)
			return new NoTranslMatrixRTS(rotateX, rotateY, rotateZ, scaleX, scaleY, scaleZ);
		else if (!rotates)
			return new NoRotMatrixRTS(translateX, translateY, translateZ, scaleX, scaleY, scaleZ);
		else if (!scales)
			return new NoScalMatrixRTS(translateX, translateY, translateZ, rotateX, rotateY, rotateZ);
		else throw new IllegalStateException("Could not resolve matrix ["+
			String.join("; ", new String[]
					{Float.toString(translateX), Float.toString(translateY), Float.toString(translateZ),
					 Float.toString(rotateX), Float.toString(rotateY), Float.toString(rotateZ),
					 Float.toString(scaleX), Float.toString(scaleY), Float.toString(scaleZ)})+
			"]");
	}
	
	protected final float
		trX, trY, trZ,
		rtX, rtY, rtZ,
		scX, scY, scZ;
	
	protected MatrixRTS(float translateX, float translateY, float translateZ,
			float rotateX, float rotateY, float rotateZ, float scaleX, float scaleY, float scaleZ) {
		this.trX = translateX; this.trY = translateY; this.trZ = translateZ;
		this.rtX = rotateX; this.rtY = rotateY; this.rtZ = rotateZ;
		this.scX = scaleX; this.scY = scaleY; this.scZ = scaleZ;
	}
	
	public abstract void apply(@NonNull PoseStack poseStack);

    @Override
    public final boolean equals(Object o) {
    	if (o == null) return false;
        if (this == o) return true;
        if (o instanceof MatrixRTS that) {
        	return this.trX == that.trX && this.trY == that.trY && this.trZ == that.trZ &&
        			this.rtX == that.rtX && this.rtY == that.rtY && this.rtZ == that.rtZ &&
        			this.scX == that.scX && this.scY == that.scY && this.scZ == that.scZ;
        } else return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.trX, this.trY, this.trZ, this.rtX, this.rtY, this.rtZ, this.scX, this.scY, this.scZ);
    }
    
    @Override
    public final String toString() {
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
    
    private static abstract class RotMatrixRTS extends MatrixRTS {
    	
        protected final Quaternionf rot;

        protected RotMatrixRTS(float trX, float trY, float trZ,
                               float rtX, float rtY, float rtZ,
                               float scX, float scY, float scZ) {
            super(trX, trY, trZ, rtX, rtY, rtZ, scX, scY, scZ);
            this.rot = new Quaternionf().rotateXYZ(
                    (float)Math.toRadians(rtX),
                    (float)Math.toRadians(rtY),
                    (float)Math.toRadians(rtZ)
                );
        }
    }

    
    private static class CompleteMatrixRTS extends RotMatrixRTS {
    	
    	protected CompleteMatrixRTS(float translateX, float translateY, float translateZ,
    			float rotateX, float rotateY, float rotateZ, float scaleX, float scaleY, float scaleZ) {
    		super(translateX, translateY, translateZ, rotateX, rotateY, rotateZ, scaleX, scaleY, scaleZ);
    	}
	
    	@Override
    	public void apply(@NonNull PoseStack poseStack) {
    		poseStack.mulPose(this.rot);
    		poseStack.scale(this.scX, this.scY, this.scZ);
    		poseStack.translate(this.trX, this.trY, this.trZ);
    	}
    	
    }
    
    private static class IdentityMatrixRTS extends MatrixRTS {
    	
    	protected IdentityMatrixRTS() {
    		super(0, 0, 0, 0, 0, 0, 1, 1, 1);
    	}
	
    	@Override
    	public void apply(@NonNull PoseStack poseStack) {}
    	
    }
    
    private static class NoRotMatrixRTS extends MatrixRTS {
    	
    	protected NoRotMatrixRTS(float translateX, float translateY, float translateZ,
    			float scaleX, float scaleY, float scaleZ) {
    		super(translateX, translateY, translateZ, 0, 0, 0, scaleX, scaleY, scaleZ);
    	}
	
    	@Override
    	public void apply(@NonNull PoseStack poseStack) {
    		poseStack.scale(this.scX, this.scY, this.scZ);
    		poseStack.translate(this.trX, this.trY, this.trZ);
    	}
    	
    }
    
    private static class NoTranslMatrixRTS extends RotMatrixRTS {
    	
    	protected NoTranslMatrixRTS(float rotateX, float rotateY, float rotateZ, float scaleX,
    			float scaleY, float scaleZ) {
    		super(0, 0, 0, rotateX, rotateY, rotateZ, scaleX, scaleY, scaleZ);
    	}
	
    	@Override
    	public void apply(@NonNull PoseStack poseStack) {
    		poseStack.mulPose(this.rot);
    		poseStack.scale(this.scX, this.scY, this.scZ);
    	}
    	
    }
    
    private static class NoScalMatrixRTS extends RotMatrixRTS {
    	
    	protected NoScalMatrixRTS(float translateX, float translateY, float translateZ,
    			float rotateX, float rotateY, float rotateZ) {
    		super(translateX, translateY, translateZ, rotateX, rotateY, rotateZ, 1, 1, 1);
    	}
	
    	@Override
    	public void apply(@NonNull PoseStack poseStack) {
    		poseStack.mulPose(this.rot);
    		poseStack.translate(this.trX, this.trY, this.trZ);
    	}
    	
    }
    
    private static class ScalOnlyMatrixRTS extends MatrixRTS {
    	
    	protected ScalOnlyMatrixRTS(float scaleX, float scaleY, float scaleZ) {
    		super(0, 0, 0, 0, 0, 0, scaleX, scaleY, scaleZ);
    	}
	
    	@Override
    	public void apply(@NonNull PoseStack poseStack) {
    		poseStack.scale(this.scX, this.scY, this.scZ);
    	}
    	
    }
    
    private static class TranslOnlyMatrixRTS extends MatrixRTS {
    	
    	protected TranslOnlyMatrixRTS(float translateX, float translateY, float translateZ) {
    		super(translateX, translateY, translateZ, 0, 0, 0, 1, 1, 1);
    	}
	
    	@Override
    	public void apply(@NonNull PoseStack poseStack) {
    		poseStack.translate(this.trX, this.trY, this.trZ);
    	}
    	
    }
    
    private static class RotOnlyMatrixRTS extends RotMatrixRTS {
    	
    	protected RotOnlyMatrixRTS(float rotateX, float rotateY, float rotateZ) {
    		super(0, 0, 0, rotateX, rotateY, rotateZ, 1, 1, 1);
    	}
	
    	@Override
    	public void apply(@NonNull PoseStack poseStack) {
    		poseStack.mulPose(this.rot);
    	}
    	
    }

}
