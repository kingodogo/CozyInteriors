package com.kingodogo.cozyinteriors.blocks;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.items.ModCreativeModeTab;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CozyInteriors.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CozyInteriors.MOD_ID);
    
    public static final Map<String, RegistryObject<Block>> CHAIR_BLOCKS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> CHAIR_ITEMS = new HashMap<>();


    public static void registerChair(String woodType) {
        String blockId = woodType + "_chair";
        LOGGER.debug("Registering chair block: {}", blockId);
        
        // Register block first
        RegistryObject<Block> block = BLOCKS.register(blockId, () -> {
            LOGGER.debug("Creating ChairBlock instance for: {}", blockId);
            return new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f));
        });
        CHAIR_BLOCKS.put(blockId, block);
        
        // Register item - the supplier will be called after blocks are registered
        final RegistryObject<Block> blockRef = block; // Final reference for lambda
        RegistryObject<Item> item = ITEMS.register(blockId, () -> {
            LOGGER.debug("Creating BlockItem for: {}", blockId);
            // Get the block from the RegistryObject - this will only be called after blocks are registered
            Block blockInstance = blockRef.get();
            return new BlockItem(blockInstance, new Item.Properties().tab(ModCreativeModeTab.COZY_INTERIORS_TAB));
        });
        CHAIR_ITEMS.put(blockId, item);
        
        LOGGER.debug("Registered chair: {} (block and item)", blockId);
    }

    public static Block getChair(String woodType) {
        RegistryObject<Block> block = CHAIR_BLOCKS.get(woodType + "_chair");
        if (block == null) {
            return null;
        }
        // Only get the block if it's actually registered (not null and has registry name)
        try {
            Block blockInstance = block.get();
            if (blockInstance != null && blockInstance.getRegistryName() != null) {
                return blockInstance;
            }
        } catch (Exception e) {
            LOGGER.warn("Error getting chair block for {}: {}", woodType, e.getMessage());
        }
        return null;
    }
    
    public static RegistryObject<Block> getChairRegistryObject(String woodType) {
        return CHAIR_BLOCKS.get(woodType + "_chair");
    }
}

