package com.kingodogo.cozyinteriors.datagen;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.ForgeRegistries;
import com.mojang.datafixers.util.Pair;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return List.of(
            Pair.of(() -> new BlockLootSubProvider()::generate, LootContextParamSets.BLOCK)
        );
    }

    private static class BlockLootSubProvider extends BlockLoot {
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
            Set<String> woodTypes = WoodTypeDetector.detectWoodTypes();
            
            for (String woodType : woodTypes) {
                String chairName = woodType + "_chair";
                Block chair = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(CozyInteriors.MOD_ID + ":" + chairName));
                if (chair != null) {
                    add(chair, createSingleItemTable(chair));
                }
            }
        }

        @Override
        protected void addTables() {
            // Not used - we use generate() instead
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> block.getRegistryName() != null && block.getRegistryName().getNamespace().equals(CozyInteriors.MOD_ID))
                ::iterator;
        }

        private LootTable.Builder createSingleItemTable(Block block) {
            return LootTable.lootTable()
                .withPool(net.minecraft.world.level.storage.loot.LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(block.asItem())));
        }
    }
}

