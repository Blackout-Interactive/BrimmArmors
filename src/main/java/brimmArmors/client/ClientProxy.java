package brimmArmors.client;

import brimmArmors.client.render.WorkbenchRender;
import brimmArmors.common.CommonProxy;
import brimmArmors.common.tile.TileRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {

	@Override
    public void client(final FMLClientSetupEvent event) {

        BlockEntityRenderers.register(TileRegistry.WORKBENCH_TILE.get(), context -> new WorkbenchRender());

    }

}
