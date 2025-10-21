package brimmArmors.common.workbench;

import java.util.Objects;
import java.util.function.Supplier;

import ema_08_.misc.IBuilder;
import net.minecraft.world.item.Item;

public class IngredientBuilder implements IBuilder<Ingredient> {
	
	private final Supplier<Item> typeProvider;
	private final int amt;
	
	public IngredientBuilder(Supplier<Item> typeProvider, int amt) {
		Objects.requireNonNull(typeProvider, "An ingredient builder must have a type provider");
		if (amt < 1) throw new IllegalArgumentException("Invalid ingredient amount");
		this.typeProvider = typeProvider;
		this.amt = amt;
	}

	@Override
	public Ingredient build() {
		return new Ingredient(this.typeProvider.get(), this.amt);
	}

}