package ema_08_.rendering.obj;

import ema_08_.rendering.geom.RTSMatricesCompound;
import net.minecraft.resources.ResourceLocation;

public interface IObjModelProvider {
	
	ResourceLocation getObj();
	
	ResourceLocation getMtl();
	
	ResourceLocation getTexturePng();
	
	String getModelName();
	
	RTSMatricesCompound getTransformations();
	
	ModelType getModelType();

}
