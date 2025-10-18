package brimmArmors.common.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

import brimmArmors.BrimmArmors;
import brimmArmors.common.items.ItemRegistry;
import brimmArmors.common.recipes.RecipesManager;
import ema_08_.geom.models.*;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BrimmArmors.MOD_ID);

    public static RegistryObject<Block> workbench = register("workbench", () ->
            new WorkbenchBlock("workbench", RecipesManager.CraftType.WORKBENCHES, 0,
            		new RTSMatricesCompoundBuilder()
            	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
            	        .setTranslate(0, -0.5f, 0)
            	        .setRotate(0, 0, 0)
            	        .setScale(20, -20, 20))
            	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
            	        .setTranslate(0, 0, 0)
            	        .setRotate(0, 0, 0)
            	        .setScale(0.5f, -0.5f, 0.5f))
            	    .build()), new Item.Properties());

    public static RegistryObject<Block> workbench_plate = register("workbench_plate", () ->
            new WorkbenchBlock("workbench_plate", RecipesManager.CraftType.PLATES, 0,
            		new RTSMatricesCompoundBuilder()
            	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
            	        .setTranslate(0, -0.5f, 0)
            	        .setRotate(0, 0, 0)
            	        .setScale(20, -20, 20))
            	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
            	        .setTranslate(0, 0, 0)
            	        .setRotate(0, 0, 0)
            	        .setScale(0.5f, -0.5f, 0.5f))
            	    .build()), new Item.Properties());

    public static RegistryObject<Block> workbench_brf = register("workbench_brf", () ->
            new WorkbenchBlock("workbench_brf", RecipesManager.CraftType.BULLETPROOFS, 0,
            		new RTSMatricesCompoundBuilder()
            	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
            	        .setTranslate(0, -0.5f, 0)
            	        .setRotate(0, 0, 0)
            	        .setScale(20, -20, 20))
            	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
            	        .setTranslate(0, 0, 0)
            	        .setRotate(0, 0, 0)
            	        .setScale(0.5f, -0.5f, 0.5f))
            	    .build()), new Item.Properties());

    public static RegistryObject<Block> workbench_hlmt = register("workbench_hlmt", () ->
            new WorkbenchBlock("workbench_hlmt", RecipesManager.CraftType.HELMETS, 15,
            		new RTSMatricesCompoundBuilder()
            	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
            	        .setTranslate(0, -0.8f, 0)
            	        .setRotate(0, 0, 0)
            	        .setScale(20, -20, 20))
            	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
            	        .setTranslate(0, 0, 0)
            	        .setRotate(0, 0, 0)
            	        .setScale(0.5f, -0.5f, 0.5f))
            	    .build()), new Item.Properties());

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier, Item.Properties properties) {
        return register(id, blockSupplier, block1 -> new BlockItem(block1, properties));
    }

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier, @Nullable Function<T, BlockItem> supplier) {
        RegistryObject<T> registryObject = BLOCKS.register(id, blockSupplier);
        if (supplier != null) {
            ItemRegistry.ITEMS.register(id, () -> supplier.apply(registryObject.get()));
        }
        return registryObject;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
