package blackoutInteractive.brimmArmors.common.workbench;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import net.minecraft.world.item.Item;

public final class Craft {
	
    private final Item result;
    private final int uid;
    private final Collection<Ingredient> ingredients;

    protected Craft(Item result, Collection<Ingredient> ingredients, int uid) {
        this.result = Objects.requireNonNull(result, "Craft must have a result item");
        this.ingredients = Collections.unmodifiableCollection(
                Objects.requireNonNull(ingredients, "Craft must have ingredients")
        );
        this.uid = uid;
    }

    public Item result() {
        return this.result;
    }

    public Collection<Ingredient> ingredients() {
        return this.ingredients;
    }
    
    public int getUid() {
    	return this.uid;
    }
}
