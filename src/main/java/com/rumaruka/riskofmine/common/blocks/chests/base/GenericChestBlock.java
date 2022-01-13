package com.rumaruka.riskofmine.common.blocks.chests.base;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.tiles.BaseChestTE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class GenericChestBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 12, 14);

    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");


    private final ChestsTypes type;
    private final BlockEntityType<? extends BaseChestTE> tileEntityTypeSupplier;
    private static final DoubleBlockCombiner.Combiner<BaseChestTE, Optional<Container>> CHEST_TE_OPTIONAL_I_CALLBACK = new DoubleBlockCombiner.Combiner<BaseChestTE, Optional<Container>>() {

        @NotNull
        @Override
        public Optional<Container> acceptDouble(BaseChestTE chestTileEntity_, BaseChestTE chestTileEntity1_) {
            return Optional.empty();
        }

        @NotNull
        @Override
        public Optional<Container> acceptSingle(BaseChestTE chestTileEntity_) {
            return Optional.of(chestTileEntity_);
        }

        @NotNull
        public Optional<Container> acceptNone() {
            return Optional.empty();
        }
    };

    public GenericChestBlock(ChestsTypes typeIn, BlockEntityType<? extends BaseChestTE> tileEntityTypeSupplierIn, BlockBehaviour.Properties propertiesIn) {
        super(propertiesIn);

        this.type = typeIn;
        this.tileEntityTypeSupplier = tileEntityTypeSupplierIn;


        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @NotNull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @NotNull
    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter reader, BlockPos pos) {
        return SHAPE;
    }

    @NotNull
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos,
                                        CollisionContext context) {

        return SHAPE;
    }

    @NotNull
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @NotNull
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return false;
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());

        return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
    }

    @NotNull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        BlockEntity tileentity = worldIn.getBlockEntity(pos);

        if (tileentity instanceof BaseChestTE) {
            if (stack.hasCustomHoverName()) {
                ((BaseChestTE) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        BlockEntity tileEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (tileEntity instanceof BaseChestTE) {
            BaseChestTE baseChestTE = (BaseChestTE) tileEntity;
            builder = builder.withDynamicDrop(CONTENTS, (lootContext, itemStackConsumer) -> {
                for (int i = 0; i < baseChestTE.getContainerSize(); ++i) {
                    itemStackConsumer.accept(baseChestTE.getItem(i));
                }
            });
        }
        return super.getDrops(state, builder);
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof BaseChestTE) {
                worldIn.updateNeighbourForOutputSignal(pos, state.getBlock());
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }


    protected Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.OPEN_CHEST);
    }

    @Override
    @Nullable
    public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        BlockEntity tileentity = world.getBlockEntity(pos);
        return tileentity instanceof MenuProvider ? (MenuProvider) tileentity : null;
    }


    @Override
    public boolean triggerEvent(BlockState state, Level worldIn, BlockPos pos, int id, int param) {
        super.triggerEvent(state, worldIn, pos, id, param);
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    private static boolean isChestBlockedAt(LevelAccessor iWorld, BlockPos blockPos) {
        return isBlockedChestByBlock(iWorld, blockPos) || isCatSittingOn(iWorld, blockPos);
    }

    private static boolean isBlockedChestByBlock(LevelAccessor iBlockReader, BlockPos worldIn) {
        BlockPos blockpos = worldIn.above();
        return iBlockReader.getBlockState(blockpos).isRedstoneConductor(iBlockReader, blockpos);
    }

    private static boolean isCatSittingOn(LevelAccessor iWorld, BlockPos blockPos) {
        List<Cat> list = iWorld.getEntitiesOfClass(Cat.class, new AABB(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 2, blockPos.getZ() + 1));
        if (!list.isEmpty()) {
            for (Cat catentity : list) {
                if (catentity.isSleeping()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromContainer((Container) worldIn.getBlockEntity(pos));
    }

    @NotNull
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @NotNull
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        return false;
    }

    public static ChestsTypes getTypeFromItem(Item itemIn) {
        return getTypeFromBlock(Block.byItem(itemIn));
    }

    public static ChestsTypes getTypeFromBlock(Block blockIn) {
        return blockIn instanceof GenericChestBlock ? ((GenericChestBlock) blockIn).getType() : null;
    }

    public ChestsTypes getType() {
        return this.type;
    }


    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return null;
    }
}