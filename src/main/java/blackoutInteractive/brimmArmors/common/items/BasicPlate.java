package blackoutInteractive.brimmArmors.common.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BasicPlate extends Item {

    private final ConcordRarity rarity;

    public BasicPlate(ConcordRarity rarity) {
        super(new Properties());
        this.rarity = rarity;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal(rarity.applyFormatting(super.getName(stack).getString()));
    }

}
