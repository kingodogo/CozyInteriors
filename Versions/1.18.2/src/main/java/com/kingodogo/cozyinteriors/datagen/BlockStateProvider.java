package com.kingodogo.cozyinteriors.datagen;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CozyInteriors.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Set<String> woodTypes = WoodTypeDetector.detectWoodTypes();
        
        for (String woodType : woodTypes) {
            String chairName = woodType + "_chair";
            ResourceLocation chairId = ResourceLocation.tryParse(CozyInteriors.MOD_ID + ":" + chairName);
            Block chairBlock = ForgeRegistries.BLOCKS.getValue(chairId);
            
            if (chairBlock != null) {
                ModelFile chairModel = models().getBuilder("block/chairs/" + chairName)
                    .parent(new ModelFile.UncheckedModelFile("block/block"))
                    .texture("planks", getPlanksTexture(woodType))
                    .texture("logs", getLogsTexture(woodType))
                    .texture("particle", getPlanksTexture(woodType));
                
                simpleBlock(chairBlock, chairModel);
            }
        }
    }

    private ResourceLocation getPlanksTexture(String woodType) {
        if (woodType.equals("mangrove") || woodType.equals("cherry") || woodType.equals("bamboo")) {
            return mcLoc("block/" + woodType + "_planks");
        }
        String namespace = "minecraft";
        if (!woodType.startsWith("minecraft:")) {
            namespace = "cozyinteriors";
        }
        return ResourceLocation.tryParse(namespace + ":block/" + woodType + "_planks");
    }

    private ResourceLocation getLogsTexture(String woodType) {
        if (woodType.equals("bamboo")) {
            return mcLoc("block/bamboo_block");
        }
        String logName = woodType.equals("mangrove") ? "mangrove_log" : woodType + "_log";
        return mcLoc("block/" + logName);
    }
}

