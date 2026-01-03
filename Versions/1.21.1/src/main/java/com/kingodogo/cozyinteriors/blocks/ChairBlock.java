package com.kingodogo.cozyinteriors.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ChairBlock extends Block {
    public ChairBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && player.startRiding(null)) {
            player.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}

