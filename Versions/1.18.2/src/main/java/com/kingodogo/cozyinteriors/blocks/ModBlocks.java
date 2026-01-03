package com.kingodogo.cozyinteriors.blocks;

import com.kingodogo.cozyinteriors.CozyInteriors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = CozyInteriors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    private static final Map<String, Block> CHAIR_BLOCKS = new HashMap<>();
    private static final Map<String, Item> CHAIR_ITEMS = new HashMap<>();

    public static void registerChair(String woodType) {
        String blockId = woodType + "_chair";
        Block block = new ChairBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.0f));
        CHAIR_BLOCKS.put(blockId, block);
        
        Item item = new BlockItem(block, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
        CHAIR_ITEMS.put(blockId, item);
    }

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        for (Map.Entry<String, Block> entry : CHAIR_BLOCKS.entrySet()) {
            entry.getValue().setRegistryName(CozyInteriors.MOD_ID, entry.getKey());
            event.getRegistry().register(entry.getValue());
        }
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        for (Map.Entry<String, Item> entry : CHAIR_ITEMS.entrySet()) {
            entry.getValue().setRegistryName(CozyInteriors.MOD_ID, entry.getKey());
            event.getRegistry().register(entry.getValue());
        }
    }

    public static Block getChair(String woodType) {
        return CHAIR_BLOCKS.get(woodType + "_chair");
    }
}

