package com.kingodogo.cozyinteriors.compat;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class WoodTypeDetector {
    public static Set<String> detectWoodTypes() {
        Set<String> woodTypes = new HashSet<>();
        
        woodTypes.add("oak");
        woodTypes.add("spruce");
        woodTypes.add("birch");
        woodTypes.add("jungle");
        woodTypes.add("acacia");
        woodTypes.add("dark_oak");
        woodTypes.add("mangrove");
        woodTypes.add("cherry");
        woodTypes.add("bamboo");

        for (Block block : ForgeRegistries.BLOCKS) {
            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block);
            if (id != null && !id.getNamespace().equals("minecraft")) {
                String path = id.getPath();
                if (path.endsWith("_planks") || path.endsWith("_logs") || path.endsWith("_stairs")) {
                    String woodType = path.replace("_planks", "").replace("_logs", "").replace("_stairs", "");
                    if (!woodType.isEmpty()) {
                        woodTypes.add(woodType);
                    }
                }
            }
        }

        return woodTypes;
    }
}

