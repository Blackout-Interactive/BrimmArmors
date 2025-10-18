package brimmArmors.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import brimmArmors.common.blocks.WorkbenchBlock;
import brimmArmors.common.tile.WorkbenchTileEntity;
import ema_08_.trivialForgeObjWrapper.ObjsManager;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WorkbenchRender implements BlockEntityRenderer<WorkbenchTileEntity> {

    public WorkbenchRender() {
        super();
    }

    @Override
    public void render(WorkbenchTileEntity tile, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BlockState state = tile.getBlockState();
        Block block = state.getBlock();

        if (block instanceof WorkbenchBlock workbench) {
            Direction facing = state.getValue(WorkbenchBlock.FACING);
            matrix.pushPose();

            matrix.translate(0.5, 0.0, 0.5);
            matrix.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));

            ObjsManager.getModel(workbench).render(matrix, buffer, combinedLight, combinedOverlay);

            matrix.popPose();
        }
    }
}
