package blackoutInteractive.brimmArmors.common.blocks;

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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import blackoutInteractive.brimmArmors.client.screens.WorkbenchScreen;
import blackoutInteractive.brimmArmors.common.tile.WorkbenchTileEntity;
import blackoutInteractive.ema_08_.rendering.geom.RTSMatricesCompound;
import blackoutInteractive.ema_08_.rendering.obj.ISingleObjModelProvider;
import blackoutInteractive.ema_08_.rendering.obj.ModelType;
import blackoutInteractive.ema_08_.rendering.obj.ObjModelReference;

import org.jetbrains.annotations.NotNull;

public class WorkbenchBlock extends Block implements ISingleObjModelProvider, EntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private final String unlocName;
    private final ObjModelReference modelRef;

    public WorkbenchBlock(String unlocName, int lightLevel,
    		RTSMatricesCompound transformations) {
        super(Properties.of().strength(3.5F).noOcclusion().lightLevel(state -> lightLevel));
        this.unlocName = unlocName;
        this.modelRef = new ObjModelReference(ModelType.BLOCKS, this.unlocName, transformations);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.@NotNull Builder builder) {
        return List.of(new ItemStack(this));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
    	if (world.isClientSide) {
    		openScreen();
    	    return InteractionResult.CONSUME;
        } else {
        	return InteractionResult.SUCCESS;
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    private static void openScreen() {
    	Minecraft.getInstance().setScreen(new WorkbenchScreen());
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
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.INVISIBLE;
    }
	
	@Override
	public WorkbenchTileEntity newBlockEntity(BlockPos pos, BlockState state) {
	    return new WorkbenchTileEntity(pos, state);
	}

	@Override
	public ObjModelReference getModelRef() {
		return this.modelRef;
	}

}
