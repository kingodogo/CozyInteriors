package com.kingodogo.cozyinteriors.blocks;

import com.kingodogo.cozyinteriors.entity.ChairEntity;
import com.kingodogo.cozyinteriors.entity.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChairBlock extends Block {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    
    // Base hitbox matching the chair model (legs + seat + backrest) - facing NORTH
    // Backrest is on the north side (z: 12-15), seat in center
    private static final VoxelShape SHAPE_NORTH = Shapes.or(
            // Leg 1: back-right (north-east corner)
            box(12.25, 0, 12.25, 14.75, 4, 14.75),
            // Leg 2: back-left (north-west corner)
            box(1.25, 0, 12.25, 3.75, 4, 14.75),
            // Leg 3: front-left (south-west corner)
            box(1.25, 0, 1.25, 3.75, 4, 3.75),
            // Leg 4: front-right (south-east corner)
            box(12.25, 0, 1.25, 14.75, 4, 3.75),
            // Seat: from [1, 4, 1] to [15, 7, 15]
            box(1, 4, 1, 15, 7, 15),
            // Backrest: from [1, 7, 12] to [15, 16, 15] (north side)
            box(1, 7, 12, 15, 16, 15)
    );
    
    // SOUTH (180°): flip z coordinates (z -> 16-z)
    private static final VoxelShape SHAPE_SOUTH = Shapes.or(
            // Leg 1: back-right (south-east corner) - was north-east
            box(12.25, 0, 1.25, 14.75, 4, 3.75),
            // Leg 2: back-left (south-west corner) - was north-west
            box(1.25, 0, 1.25, 3.75, 4, 3.75),
            // Leg 3: front-left (north-west corner) - was south-west
            box(1.25, 0, 12.25, 3.75, 4, 14.75),
            // Leg 4: front-right (north-east corner) - was south-east
            box(12.25, 0, 12.25, 14.75, 4, 14.75),
            // Seat: same (centered)
            box(1, 4, 1, 15, 7, 15),
            // Backrest: from [1, 7, 1] to [15, 16, 4] (south side)
            box(1, 7, 1, 15, 16, 4)
    );
    
    // EAST (90° clockwise): x' = z, z' = 16-x
    // Backrest moves from z=12-15 (north) to x=12-15 (east)
    private static final VoxelShape SHAPE_EAST = Shapes.or(
            // Leg 1: back-right (east-south corner) - was north-east (x=12.25-14.75, z=12.25-14.75)
            // Rotated: x'=z=12.25-14.75, z'=16-x=1.25-3.75
            box(12.25, 0, 1.25, 14.75, 4, 3.75),
            // Leg 2: back-left (east-north corner) - was north-west (x=1.25-3.75, z=12.25-14.75)
            // Rotated: x'=z=12.25-14.75, z'=16-x=12.25-14.75
            box(12.25, 0, 12.25, 14.75, 4, 14.75),
            // Leg 3: front-left (west-north corner) - was south-west (x=1.25-3.75, z=1.25-3.75)
            // Rotated: x'=z=1.25-3.75, z'=16-x=12.25-14.75
            box(1.25, 0, 12.25, 3.75, 4, 14.75),
            // Leg 4: front-right (west-south corner) - was south-east (x=12.25-14.75, z=1.25-3.75)
            // Rotated: x'=z=1.25-3.75, z'=16-x=1.25-3.75
            box(1.25, 0, 1.25, 3.75, 4, 3.75),
            // Seat: same (centered, rotation doesn't change it)
            box(1, 4, 1, 15, 7, 15),
            // Backrest: from [12, 7, 1] to [15, 16, 15] (east side)
            // Was z=12-15, rotated: x'=z=12-15, z'=16-x=1-15
            box(12, 7, 1, 15, 16, 15)
    );
    
    // WEST (270° clockwise = 90° counter-clockwise): x' = 16-z, z' = x
    // Backrest moves from z=12-15 (north) to x=1-4 (west)
    private static final VoxelShape SHAPE_WEST = Shapes.or(
            // Leg 1: back-right (west-north corner) - was north-east (x=12.25-14.75, z=12.25-14.75)
            // Rotated: x'=16-z=1.25-3.75, z'=x=12.25-14.75
            box(1.25, 0, 12.25, 3.75, 4, 14.75),
            // Leg 2: back-left (west-south corner) - was north-west (x=1.25-3.75, z=12.25-14.75)
            // Rotated: x'=16-z=1.25-3.75, z'=x=1.25-3.75
            box(1.25, 0, 1.25, 3.75, 4, 3.75),
            // Leg 3: front-left (east-south corner) - was south-west (x=1.25-3.75, z=1.25-3.75)
            // Rotated: x'=16-z=12.25-14.75, z'=x=1.25-3.75
            box(12.25, 0, 1.25, 14.75, 4, 3.75),
            // Leg 4: front-right (east-north corner) - was south-east (x=12.25-14.75, z=1.25-3.75)
            // Rotated: x'=16-z=12.25-14.75, z'=x=12.25-14.75
            box(12.25, 0, 12.25, 14.75, 4, 14.75),
            // Seat: same (centered, rotation doesn't change it)
            box(1, 4, 1, 15, 7, 15),
            // Backrest: from [1, 7, 1] to [4, 16, 15] (west side)
            // Was z=12-15, rotated: x'=16-z=1-4, z'=x=1-15
            box(1, 7, 1, 4, 16, 15)
    );

    public ChairBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Get the direction the player is facing (horizontal only)
        Direction facing = context.getHorizontalDirection();
        // Chair faces the same direction as the player
        return this.defaultBlockState().setValue(FACING, facing);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return switch (facing) {
            case NORTH -> SHAPE_SOUTH;  // Swapped to match texture
            case SOUTH -> SHAPE_NORTH;   // Swapped to match texture
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_SOUTH;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state, level, pos, context);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // Check if player is sneaking (unsit)
        if (player.isShiftKeyDown()) {
            // Find and remove chair entity if player is riding it
            if (player.getVehicle() instanceof ChairEntity) {
                player.stopRiding();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        // Check if there's already a chair entity here
        if (!level.getEntitiesOfClass(ChairEntity.class, 
                net.minecraft.world.phys.AABB.ofSize(new net.minecraft.world.phys.Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5), 1, 1, 1)).isEmpty()) {
            return InteractionResult.PASS;
        }

        // Create and spawn chair entity
        ChairEntity chair = new ChairEntity(level, pos);
        if (level.addFreshEntity(chair)) {
            player.startRiding(chair);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        // Remove chair entities when block is broken
        if (!state.is(newState.getBlock())) {
            level.getEntitiesOfClass(ChairEntity.class, 
                    net.minecraft.world.phys.AABB.ofSize(new net.minecraft.world.phys.Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5), 1, 1, 1))
                    .forEach(entity -> entity.remove(Entity.RemovalReason.DISCARDED));
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}

