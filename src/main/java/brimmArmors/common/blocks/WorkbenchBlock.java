package brimmArmors.common.blocks;

import ema_08_.geom.models.RTSMatricesCompound;
import ema_08_.trivialForgeObjWrapper.IDefaultObjModelProvider;
import ema_08_.trivialForgeObjWrapper.ModelType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


import java.util.List;

import brimmArmors.BrimmArmors;
import brimmArmors.common.network.packets.SetWorkbenchScreenS2C;
import brimmArmors.common.recipes.RecipesManager;

public class WorkbenchBlock extends Block implements IDefaultObjModelProvider {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public final RecipesManager.CraftType craftType;
    private final String unlocName;
    private final RTSMatricesCompound transformations;

    public WorkbenchBlock(String unlocName, RecipesManager.CraftType craftType, int lightLevel,
    		RTSMatricesCompound transformations) {
        super(Properties.of().strength(3.5F).noOcclusion().lightLevel(state -> lightLevel));
        this.craftType = craftType;
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
        if (!world.isClientSide()) {
            if (player instanceof ServerPlayer serverPlayer) {
                BrimmArmors.network.sendTo(new SetWorkbenchScreenS2C(craftType), serverPlayer);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
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
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        // Define shapes using correct 0-16 coordinates for the block
        return switch (direction) {
            case NORTH -> Shapes.box(0, 0, 0, 2, 1, 1);  // example smaller shape
            case SOUTH -> Shapes.box(14 / 16f, 0, 0, 1, 1, 1);
            case EAST -> Shapes.box(0, 0, 14 / 16f, 1, 1, 1);
            case WEST -> Shapes.box(0, 0, 0, 1 / 8f, 1, 1);
            default -> Shapes.block();
        };
    }
    
    @Override
    public RenderShape getRenderShape(BlockState state) {
        // INVISIBLE to allow custom rendering
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
}
