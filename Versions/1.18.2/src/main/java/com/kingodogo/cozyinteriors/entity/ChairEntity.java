package com.kingodogo.cozyinteriors.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class ChairEntity extends Entity {
    private BlockPos chairPos;

    public ChairEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public ChairEntity(Level level, BlockPos pos) {
        this(ModEntityTypes.CHAIR.get(), level);
        this.chairPos = pos;
        // Position entity at seat level (seat is at y=4 to y=7 in model, center is ~0.34375)
        // We position at 0.4375 (top of seat) so player can sit on it
        this.setPos(pos.getX() + 0.5, pos.getY() + 0.4375, pos.getZ() + 0.5);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            return;
        }

        // Check if chair block still exists
        if (this.chairPos != null) {
            BlockState state = this.level.getBlockState(this.chairPos);
            if (state.isAir() || !(state.getBlock() instanceof com.kingodogo.cozyinteriors.blocks.ChairBlock)) {
                this.remove(RemovalReason.DISCARDED);
                return;
            }
        }

        // Remove if no passengers
        if (this.getPassengers().isEmpty()) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected boolean canRide(Entity entity) {
        return true;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        if (this.chairPos != null) {
            return new Vec3(this.chairPos.getX() + 0.5, this.chairPos.getY() + 1.0, this.chairPos.getZ() + 0.5);
        }
        return super.getDismountLocationForPassenger(passenger);
    }

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        // Adjust player position down by 3 pixels (0.1875 blocks) from entity center
        // This makes the player sit slightly lower, sinking into the seat
        if (passenger instanceof LivingEntity) {
            Vec3 pos = this.getRidingPosition((LivingEntity) passenger);
            passenger.setPos(pos.x, pos.y - 0.1875, pos.z);
        }
    }

    private Vec3 getRidingPosition(LivingEntity passenger) {
        // Get the base riding position
        double yOffset = this.getPassengersRidingOffset() + passenger.getMyRidingOffset();
        return new Vec3(this.getX(), this.getY() + yOffset, this.getZ());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

