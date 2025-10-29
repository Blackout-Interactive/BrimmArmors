package blackoutInteractive.brimmArmors.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import blackoutInteractive.brimmArmors.common.blocks.WorkbenchBlock;
import blackoutInteractive.brimmArmors.common.tile.WorkbenchTileEntity;
import blackoutInteractive.ema_08_.rendering.obj.ObjsManager;
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
            float yRot = switch (facing) {
            case NORTH -> 180f;
            case SOUTH -> 0f;
            case EAST  -> -90f;
            case WEST  -> 90f;
            default -> 0f;
            };
            if (yRot != 0) matrix.mulPose(Axis.YP.rotationDegrees(yRot));

            ObjsManager.getModel(workbench).render(matrix, buffer, combinedLight, combinedOverlay, partialTicks);

            matrix.popPose();
        }
    }
}
