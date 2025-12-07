package blackoutInteractive.brimmArmors.common.recipes;

import blackoutInteractive.brimmArmors.common.items.ArmorPatch;
import blackoutInteractive.brimmArmors.common.items.BrimmArmor;
import blackoutInteractive.brimmArmors.common.registries.RecipeSerializersRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class PatchRemoveRecipe extends CustomRecipe {

    public PatchRemoveRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {
        ItemStack armor = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.getItem() instanceof BrimmArmor basic) {
                if (!armor.isEmpty()) return false;
                if (basic.getPatch(stack) == null) return false;
                armor = stack;
            } else {
                return false;
            }
        }

        return !armor.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
        ItemStack armor = ItemStack.EMPTY;
        ArmorPatch patch = null;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof BrimmArmor basic) {
                armor = stack;
                patch = basic.getPatch(stack);
            }
        }

        if (armor.isEmpty() || patch == null) return ItemStack.EMPTY;

        ItemStack resultArmor = armor.copy();
        ((BrimmArmor) armor.getItem()).removePatch(resultArmor);

        return resultArmor;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof BrimmArmor basic) {
                ArmorPatch patch = basic.getPatch(stack);
                if (patch != null) {
                    remaining.set(i, new ItemStack(patch));
                }
            }
        }

        return remaining;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.PATCH_REMOVE.get();
    }
}
