package brimmArmors.common.workbench;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import net.minecraft.world.item.Item;

public final class Craft {
	
	private static int uidc = 1;

    private final Item result;
    private final int uid;
    private final Collection<Ingredient> ingredients;

    public Craft(Item result, Collection<Ingredient> ingredients) {
        this.result = Objects.requireNonNull(result, "Craft must have a result item");
        this.ingredients = Collections.unmodifiableCollection(
                Objects.requireNonNull(ingredients, "Craft must have ingredients")
        );
        this.uid = uidc++;
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
