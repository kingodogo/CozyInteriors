package com.kingodogo.cozyinteriors.datagen;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.blocks.ModBlocks;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class LootTableProvider extends BlockLoot {
    public LootTableProvider(DataGenerator generator) {
    }

    @Override
    protected void addTables() {
        Set<String> woodTypes = WoodTypeDetector.detectWoodTypes();
        
        for (String woodType : woodTypes) {
            Block chair = ModBlocks.getChair(woodType);
            if (chair != null) {
                dropSelf(chair);
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return java.util.Collections.emptyList();
    }
}

