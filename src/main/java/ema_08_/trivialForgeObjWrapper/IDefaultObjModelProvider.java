package ema_08_.trivialForgeObjWrapper;

import brimmArmors.BrimmArmors;
import net.minecraft.resources.ResourceLocation;

public interface IDefaultObjModelProvider extends IObjModelProvider {
	
	@Override
	default ResourceLocation getObj() {
		return new ResourceLocation(BrimmArmors.MOD_ID, getModelType().non_namespaced_resloc_obj_base+getModelName()+".obj");
	}
	
	@Override
	default ResourceLocation getMtl() {
		return new ResourceLocation(BrimmArmors.MOD_ID, getModelType().non_namespaced_resloc_mtl_base+getModelName()+".mtl");
	}
	
	@Override
	default ResourceLocation getTexturePng() {
		return new ResourceLocation(BrimmArmors.MOD_ID, getModelType().non_namespaced_resloc_png_base+getModelName()+".png");
	}

}
