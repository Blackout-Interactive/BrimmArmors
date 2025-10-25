package brimmArmors.common.blocks;

import ema_08_.rendering.geom.RTSMatricesCompound;
import ema_08_.rendering.obj.IDefaultObjModelProvider;
import ema_08_.rendering.obj.ModelType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

import brimmArmors.client.screens.WorkbenchScreen;
import brimmArmors.common.tile.WorkbenchTileEntity;

public class WorkbenchBlock extends Block implements IDefaultObjModelProvider, EntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private final String unlocName;
    private final RTSMatricesCompound transformations;

    public WorkbenchBlock(String unlocName, int lightLevel,
    		RTSMatricesCompound transformations) {
        super(Properties.of().strength(3.5F).noOcclusion().lightLevel(state -> lightLevel));
        this.unlocName = unlocName;
        this.transformations = transformations;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        return List.of(new ItemStack(this));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    	if (world.isClientSide) {
    		Minecraft.getInstance().setScreen(new WorkbenchScreen());
    	    return InteractionResult.CONSUME;
        } else {
        	return InteractionResult.SUCCESS;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            Direction facing = Direction.getNearest(
                    player.getLookAngle().x,
                    0,
                    player.getLookAngle().z
            );
            boolean flag = facing == Direction.SOUTH || facing == Direction.NORTH;
            return this.defaultBlockState().setValue(FACING, flag ? facing : facing.getOpposite());
        }
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

	@Override
	public String getModelName() {
		return this.unlocName;
	}

	@Override
	public ModelType getModelType() {
		return ModelType.BLOCKS;
	}

	@Override
	public RTSMatricesCompound getTransformations() {
		return this.transformations;
	}
	
	@Override
	public WorkbenchTileEntity newBlockEntity(BlockPos pos, BlockState state) {
	    return new WorkbenchTileEntity(pos, state);
	}

}
