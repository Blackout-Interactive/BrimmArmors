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
import ema_08_.rendering.geom.RTSMatricesCompound;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BrimmArmors.MOD_ID);

    public static final RegistryObject<Block> workbench = register("workbench", () ->
            new WorkbenchBlock("workbench", 0, RTSMatricesCompound.EMPTY), new Item.Properties());

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier, Item.Properties properties) {
        return register(id, blockSupplier, block -> new BlockItem(block, properties));
    }

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier, @Nullable Function<T, BlockItem> supplier) {
        RegistryObject<T> registryObject = BLOCKS.register(id, blockSupplier);
        if (supplier != null) {
            ItemRegistry.registerAndAddToMiscTab(id, () -> supplier.apply(registryObject.get()));
        }
        return registryObject;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
