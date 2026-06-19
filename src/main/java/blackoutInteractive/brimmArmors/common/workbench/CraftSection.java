package blackoutInteractive.brimmArmors.common.workbench;

import java.util.Objects;

import blackoutInteractive.brimmArmors.BrimmArmors;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;

public enum CraftSection {
	
	HELMETS("helmets"),
	
	CHESTPLATES("chestplates"),
	
	PLATES("plates"),
	
	LEGGINGS("leggings"),
	
	PATCHES("patches");
	
	public static CraftSection ofArmor(ArmorItem.Type type) {
		  Objects.requireNonNull(type);
		  switch (type){
		  case HELMET: return HELMETS;
		  case CHESTPLATE: return CHESTPLATES;
		  case LEGGINGS: return LEGGINGS;
		  default: {
			  throw new IllegalArgumentException("Unexpected armor type "+type);
		  }
		  }
	}
	
	private final String name;
	
	CraftSection(String name) {
		this.name = name;
	}
	
	public String localisedName() {
		return Component.literal(I18n.get("screen." + BrimmArmors.MOD_ID + ".workbench.section." + this.name)).getString();
	}

}
