package blackoutInteractive.brimmArmors.common.items;

import blackoutInteractive.ema_08_.rendering.twoToThreeD.IDefaultPatchesRenderablePngProvider;
import net.minecraft.world.item.Item;

public class ArmorPatch extends Item implements IDefaultPatchesRenderablePngProvider {
	
	private final String unlocName;

	public ArmorPatch(String unlocName) {
		super(new Properties());
		this.unlocName = unlocName;
	}
	
	public String getPatchName() {
		return this.unlocName;
	}

	@Override
	public String getTextureName() {
		return this.unlocName;
	}
	
}
