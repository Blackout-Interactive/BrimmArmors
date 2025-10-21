package brimmArmors.common.tile;

import brimmArmors.BrimmArmors;
import brimmArmors.common.blocks.BlockRegistry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BrimmArmors.MOD_ID);

    public static final RegistryObject<BlockEntityType<WorkbenchTileEntity>> WORKBENCH_TILE = BLOCK_ENTITIES.register("workbench_tile", () ->
            BlockEntityType.Builder.of(WorkbenchTileEntity::new,
                            BlockRegistry.workbench.get()
                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
