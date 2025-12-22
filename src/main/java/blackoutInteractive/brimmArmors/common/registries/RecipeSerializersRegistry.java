package blackoutInteractive.brimmArmors.common.registries;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.common.recipes.PatchAddRecipe;
import blackoutInteractive.brimmArmors.common.recipes.PatchRemoveRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializersRegistry {
	
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BrimmArmors.MOD_ID);

    public static final RegistryObject<SimpleCraftingRecipeSerializer<PatchAddRecipe>> PATCH_ADD =
            SERIALIZERS.register("patch_add", () -> new SimpleCraftingRecipeSerializer<>(PatchAddRecipe::new));
    
    public static final RegistryObject<SimpleCraftingRecipeSerializer<PatchRemoveRecipe>> PATCH_REMOVE =
            SERIALIZERS.register("patch_remove", () -> new SimpleCraftingRecipeSerializer<>(PatchRemoveRecipe::new));


    public static void register(net.minecraftforge.eventbus.api.IEventBus bus) {
        SERIALIZERS.register(bus);
    }
    
}