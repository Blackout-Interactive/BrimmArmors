package blackoutInteractive.brimmArmors.common.recipes;

import blackoutInteractive.brimmArmors.common.items.ArmorPatch;
import blackoutInteractive.brimmArmors.common.items.BrimmArmor;
import blackoutInteractive.brimmArmors.common.registries.RecipeSerializersRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class PatchAddRecipe extends CustomRecipe {

    public PatchAddRecipe(ResourceLocation id, CraftingBookCategory cat) {
        super(id, cat);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level level) {
        ItemStack armor = ItemStack.EMPTY;
        ItemStack patch = ItemStack.EMPTY;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.isEmpty()) continue;
            if (stack.getItem() instanceof BrimmArmor basic) {
                if (!armor.isEmpty()) return false;
                if (basic.getPatch(stack) != null) return false;
                armor = stack;
            } else if (stack.getItem() instanceof ArmorPatch) {
                if (!patch.isEmpty()) return false;
                patch = stack;
            } else {
                return false;
            }
        }

        return !armor.isEmpty() && !patch.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
        ItemStack armor = ItemStack.EMPTY;
        ArmorPatch patchItem = null;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof BrimmArmor) armor = stack;
            if (stack.getItem() instanceof ArmorPatch) patchItem = (ArmorPatch) stack.getItem();
        }

        if (armor.isEmpty() || patchItem == null) return ItemStack.EMPTY;

        ItemStack result = armor.copy();
        ((BrimmArmor) armor.getItem()).setPatch(result, patchItem);
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersRegistry.PATCH_ADD.get();
    }
}
