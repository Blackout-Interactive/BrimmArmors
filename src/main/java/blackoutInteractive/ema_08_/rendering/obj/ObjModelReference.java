package blackoutInteractive.ema_08_.rendering.obj;

import java.util.Objects;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.ema_08_.rendering.geom.RTSMatricesCompound;
import net.minecraft.resources.ResourceLocation;

public final class ObjModelReference {
	
	public final ModelType modelType;

	public final ResourceLocation
		objFile, mtlFile, pngFile;
	
	public final String modelName;
	
	public final RTSMatricesCompound modelTransforms;
	
	public ObjModelReference(ModelType mty, String mnm, RTSMatricesCompound mtr) {
		this.modelType = Objects.requireNonNull(mty);
		this.modelName = Objects.requireNonNull(mnm);
		this.modelTransforms = Objects.requireNonNull(mtr);
		this.objFile = new ResourceLocation(BrimmArmors.MOD_ID, mty.non_namespaced_resloc_obj_base+mnm+".obj");
		this.mtlFile = new ResourceLocation(BrimmArmors.MOD_ID, mty.non_namespaced_resloc_mtl_base+mnm+".mtl");
		this.pngFile = new ResourceLocation(BrimmArmors.MOD_ID, mty.non_namespaced_resloc_png_base+mnm+".mtl");
	}

}
