package blackoutInteractive.brimmArmors.common.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BrimmPlate extends Item {

    private final BrimmRarity rarity;

    public BrimmPlate(BrimmRarity rarity) {
        super(new Properties());
        this.rarity = rarity;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return Component.literal(rarity.applyFormatting(super.getName(stack).getString()));
    }

}
