package com.kingodogo.cozyinteriors.blocks;

import com.kingodogo.cozyinteriors.CozyInteriors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CozyInteriors.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CozyInteriors.MOD_ID);
    
    private static final Map<String, RegistryObject<Block>> CHAIR_BLOCKS = new HashMap<>();
    private static final Map<String, RegistryObject<Item>> CHAIR_ITEMS = new HashMap<>();

    public static void register() {
        BLOCKS.register(CozyInteriors.MOD_EVENT_BUS);
        ITEMS.register(CozyInteriors.MOD_EVENT_BUS);
    }

    public static void registerChair(String woodType) {
        String blockId = woodType + "_chair";
        RegistryObject<Block> block = BLOCKS.register(blockId, () -> 
            new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f)));
        CHAIR_BLOCKS.put(blockId, block);
        
        RegistryObject<Item> item = ITEMS.register(blockId, () -> 
            new BlockItem(block.get(), new Item.Properties()));
        CHAIR_ITEMS.put(blockId, item);
    }

    public static Block getChair(String woodType) {
        RegistryObject<Block> block = CHAIR_BLOCKS.get(woodType + "_chair");
        return block != null ? block.get() : null;
    }
}

