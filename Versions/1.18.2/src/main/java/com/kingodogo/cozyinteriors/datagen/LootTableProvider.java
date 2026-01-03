package com.kingodogo.cozyinteriors.datagen;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public class LootTableProvider extends BlockLoot {
    public LootTableProvider(DataGenerator generator) {
    }

    @Override
    protected void addTables() {
        Set<String> woodTypes = WoodTypeDetector.detectWoodTypes();
        
        for (String woodType : woodTypes) {
            String chairName = woodType + "_chair";
            Block chair = ForgeRegistries.BLOCKS.getValue(new net.minecraft.resources.ResourceLocation(CozyInteriors.MOD_ID, chairName));
            if (chair != null) {
                dropSelf(chair);
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
            .filter(block -> block.getRegistryName() != null && block.getRegistryName().getNamespace().equals(CozyInteriors.MOD_ID))
            ::iterator;
    }
}

