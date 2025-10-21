package brimmArmors.common.workbench;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import ema_08_.misc.IBuilder;
import net.minecraft.world.item.Item;

public class CraftBuilder implements IBuilder<Craft> {

    private final Supplier<Item> resultSupp;
    private final Collection<IngredientBuilder> ingredients = new ArrayList<>();

    public CraftBuilder(Supplier<Item> resultSupp) {
        this.resultSupp = Objects.requireNonNull(resultSupp, "Craft builder must have a result supplier");
    }

    public CraftBuilder addIngredient(IngredientBuilder builder) {
        this.ingredients.add(builder);
        return this;
    }

    public Craft build() {
        if (this.ingredients.isEmpty()) {
            throw new IllegalStateException("Craft must have at least one ingredient");
        }
        return new Craft(this.resultSupp.get(),
        		this.ingredients.stream().map(IngredientBuilder::build).collect(Collectors.toList()));
    }
}
