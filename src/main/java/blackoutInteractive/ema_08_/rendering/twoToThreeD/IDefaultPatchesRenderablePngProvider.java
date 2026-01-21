package blackoutInteractive.ema_08_.rendering.twoToThreeD;

import blackoutInteractive.brimmArmors.BrimmArmors;
import net.minecraft.resources.ResourceLocation;

public interface IDefaultPatchesRenderablePngProvider extends IRenderablePngProvider {
	
	@Override
	default ResourceLocation getTexturePng() {
		return new ResourceLocation(BrimmArmors.MOD_ID, "textures/patches/"+getTextureName()+".png");
	}

}
