package com.kingodogo.cozyinteriors.blocks;

import com.kingodogo.cozyinteriors.CozyInteriors;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CozyInteriors.MOD_ID);

    public static final Map<String, RegistryObject<Block>> CHAIR_BLOCKS = new HashMap<>();

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        registerVanillaChairs();
    }

    private static void registerVanillaChairs() {
        String[] woodTypes = {"oak", "spruce", "birch", "acacia", "jungle", "dark_oak", "mangrove", "cherry", "bamboo"};
        for (String woodType : woodTypes) {
            registerChair(woodType);
        }
    }

    public static void registerChair(String woodType) {
        String name = woodType + "_chair";
        RegistryObject<Block> chair = BLOCKS.register(name, () -> new ChairBlock(
            BlockBehaviour.Properties.of(Material.WOOD)
                .strength(2.0f)
        ));
        CHAIR_BLOCKS.put(woodType, chair);
    }
}

