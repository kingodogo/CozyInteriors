package com.kingodogo.cozyinteriors;

import com.kingodogo.cozyinteriors.blocks.ModBlocks;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import com.kingodogo.cozyinteriors.entity.ModEntityTypes;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;
import org.slf4j.Logger;

import java.util.Set;

@Mod(CozyInteriors.MOD_ID)
public class CozyInteriors {

    public static final String MOD_ID = "cozyinteriors";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CozyInteriors() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // CRITICAL: Register chairs BEFORE registering DeferredRegisters to event bus
        // This ensures they're registered when the registry events fire
        Set<String> woodTypes = WoodTypeDetector.detectWoodTypes();
        LOGGER.info("Detected {} wood types", woodTypes.size());
        
        for (String woodType : woodTypes) {
            ModBlocks.registerChair(woodType);
        }
        LOGGER.info("Registered {} chair blocks and items to DeferredRegister", woodTypes.size());
        
        // NOW register the DeferredRegisters to the event bus
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.ITEMS.register(modEventBus);
        ModEntityTypes.ENTITY_TYPES.register(modEventBus);
        LOGGER.info("Registered DeferredRegisters for blocks, items, and entities to event bus");
        
        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("CozyInteriors initialized");
    }
}

