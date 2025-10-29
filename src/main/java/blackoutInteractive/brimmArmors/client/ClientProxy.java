package blackoutInteractive.brimmArmors.client;

import blackoutInteractive.brimmArmors.client.render.WorkbenchRender;
import blackoutInteractive.brimmArmors.common.CommonProxy;
import blackoutInteractive.brimmArmors.common.registries.TileRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {

	@Override
    public void client(final FMLClientSetupEvent event) {

        BlockEntityRenderers.register(TileRegistry.WORKBENCH_TILE.get(), context -> new WorkbenchRender());

    }

}
