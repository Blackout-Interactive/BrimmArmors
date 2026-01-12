package blackoutInteractive.brimmArmors.common.workbench;

import java.util.Objects;

import net.minecraft.world.item.Item;

public final record Ingredient(Item type, int amt) {
	
	public Ingredient {
		Objects.requireNonNull(type, "An ingredient must declare a type");
		if (amt < 1) throw new IllegalArgumentException("Invalid ingredient amount");
	}

}
