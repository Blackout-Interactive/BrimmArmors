package ema_08_.rendering.twoToThreeD;

import ema_08_.rendering.geom.RTSMatricesCompound;
import net.minecraft.resources.ResourceLocation;

public interface I3dTextureProvider {
	
	ResourceLocation getTexturePng();
	
	String getTextureName();
	
	RTSMatricesCompound getTexturePosTransformations();
	
	Texture3dPositions getTexturePositon();

}
