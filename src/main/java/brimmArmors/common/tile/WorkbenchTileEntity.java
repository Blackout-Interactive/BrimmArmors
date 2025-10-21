package brimmArmors.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WorkbenchTileEntity extends BlockEntity {

    public WorkbenchTileEntity(BlockPos pos, BlockState state) {
        super(TileRegistry.WORKBENCH_TILE.get(), pos, state);
    }

}
