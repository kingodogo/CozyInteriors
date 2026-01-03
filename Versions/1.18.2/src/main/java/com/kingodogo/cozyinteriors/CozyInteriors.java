package com.kingodogo.cozyinteriors;

import com.kingodogo.cozyinteriors.blocks.ModBlocks;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Set;

@Mod(CozyInteriors.MOD_ID)
public class CozyInteriors {

    public static final String MOD_ID = "cozyinteriors";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CozyInteriors() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Set<String> woodTypes = WoodTypeDetector.detectWoodTypes();
            for (String woodType : woodTypes) {
                ModBlocks.registerChair(woodType);
            }
            LOGGER.info("Registered {} chair types", woodTypes.size());
        });
        LOGGER.info("CozyInteriors initialized");
    }
}

