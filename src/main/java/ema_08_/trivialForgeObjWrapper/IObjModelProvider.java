package ema_08_.trivialForgeObjWrapper;

import ema_08_.geom.models.RTSMatricesCompound;
import net.minecraft.resources.ResourceLocation;

public interface IObjModelProvider {
	
	ResourceLocation getObj();
	
	ResourceLocation getMtl();
	
	ResourceLocation getTexturePng();
	
	String getModelName();
	
	RTSMatricesCompound getTransformations();
	
	ModelType getModelType();

}
