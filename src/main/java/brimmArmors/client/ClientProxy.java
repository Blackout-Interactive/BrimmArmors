package brimmArmors.client;

import brimmArmors.client.render.WorkbenchRender;
import brimmArmors.common.CommonProxy;
import brimmArmors.common.blocks.BlockRegistry;
import brimmArmors.common.tile.TileRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {

    @SuppressWarnings("deprecation")
	@Override
    public void client(final FMLClientSetupEvent event) {
        // Block Entity Renderer registration (formerly TileEntityRenderer)
        BlockEntityRenderers.register(TileRegistry.WORKBENCH_TILE.get(), context -> new WorkbenchRender());

        // Render layer setup (formerly RenderTypeLookup)
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.workbench.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.workbench_plate.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.workbench_brf.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistry.workbench_hlmt.get(), RenderType.cutout());
    }

}
