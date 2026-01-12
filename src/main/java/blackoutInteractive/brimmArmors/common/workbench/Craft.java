package blackoutInteractive.brimmArmors.common.workbench;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final record Craft(
		Item result,
		Collection<Ingredient> ingredients,
		ResourceLocation id) {
	
    public Craft(Item result, Collection<Ingredient> ingredients, ResourceLocation id) {
        this.result = Objects.requireNonNull(result, "Craft must have a result item");
        this.ingredients = Collections.unmodifiableCollection(
                Objects.requireNonNull(ingredients, "Craft must have ingredients")
        );
        this.id = Objects.requireNonNull(id, "Craft must have an id");
    }

}
