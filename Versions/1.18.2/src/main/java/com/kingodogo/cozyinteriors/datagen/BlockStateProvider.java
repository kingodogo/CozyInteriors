package com.kingodogo.cozyinteriors.datagen;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.blocks.ModBlocks;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
            
            // Get block from ModBlocks registry
            RegistryObject<Block> chairBlockObj = ModBlocks.CHAIR_BLOCKS.get(chairName);
            
            if (chairBlockObj == null || !chairBlockObj.isPresent()) {
                // Block not registered yet, skip
                continue;
            }
            
            Block chairBlock = chairBlockObj.get();
            
            // Create model that inherits from template_chair with wood-specific textures
            // Template uses #planks and #logs, so we define those texture variables
            ModelFile chairModel = models().withExistingParent("block/chairs/" + chairName, modLoc("block/chairs/template_chair"))
                .texture("planks", getPlanksTexture(woodType))
                .texture("logs", getLogsTexture(woodType))
                .texture("particle", getPlanksTexture(woodType));
            
            // Generate blockstate with rotation variants (north, south, east, west)
            // Note: EAST and WEST are swapped to match hitbox orientation
            getVariantBuilder(chairBlock)
                .forAllStates(state -> {
                    net.minecraft.core.Direction facing = state.getValue(com.kingodogo.cozyinteriors.blocks.ChairBlock.FACING);
                    int rotationY = 0;
                    switch (facing) {
                        case NORTH -> rotationY = 180;  // Swapped from 0 to match hitbox
                        case EAST -> rotationY = 270;   // Swapped from 90 to match hitbox
                        case SOUTH -> rotationY = 0;    // Swapped from 180 to match hitbox
                        case WEST -> rotationY = 90;    // Swapped from 270 to match hitbox
                    }
                    return new net.minecraftforge.client.model.generators.ConfiguredModel[]{
                        new net.minecraftforge.client.model.generators.ConfiguredModel(
                            chairModel, 0, rotationY, false
                        )
                    };
                });
            
            // Generate item model that parents the block model
            // This automatically handles ALL wood types (vanilla + modded) detected by WoodTypeDetector
            // No manual item model creation needed - this loop generates them for every detected wood type
            // According to Forge docs: "The parent can be any ModelFile: generated or existing"
            // Since chairModel is created first, it's available as a ModelFile for the item model
            // The block model already has all textures defined, so the item model will inherit them
            itemModels().getBuilder(chairName)
                .parent(chairModel);
        }
    }

    private ResourceLocation getPlanksTexture(String woodType) {
        // Try to find the actual planks block in the registry to get the correct namespace
        for (net.minecraft.world.level.block.Block block : ForgeRegistries.BLOCKS) {
            ResourceLocation blockId = block.getRegistryName();
            if (blockId != null) {
                String path = blockId.getPath();
                // Match planks blocks (handles vanilla and modded)
                if (path.equals(woodType + "_planks") || path.equals(woodType + "_plank")) {
                    // Derive texture location from block registry name (parse namespace from string)
                    String idString = blockId.toString();
                    int colonIndex = idString.indexOf(':');
                    if (colonIndex > 0) {
                        String namespace = idString.substring(0, colonIndex);
                        return ResourceLocation.tryParse(namespace + ":block/" + path);
                    }
                }
            }
        }
        
        // Fallback: try common patterns
        // Vanilla woods
        String[] vanillaWoods = {"oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo"};
        for (String vanilla : vanillaWoods) {
            if (woodType.equals(vanilla)) {
                return mcLoc("block/" + woodType + "_planks");
            }
        }
        
        // Default fallback
        return mcLoc("block/oak_planks");
    }

    private ResourceLocation getLogsTexture(String woodType) {
        // Try to find the actual log block in the registry to get the correct namespace
        for (net.minecraft.world.level.block.Block block : ForgeRegistries.BLOCKS) {
            ResourceLocation blockId = block.getRegistryName();
            if (blockId != null) {
                String path = blockId.getPath();
                // Match log blocks (handles vanilla and modded)
                if (path.equals(woodType + "_log") || path.equals(woodType + "_logs")) {
                    // Derive texture location from block registry name (parse namespace from string)
                    String idString = blockId.toString();
                    int colonIndex = idString.indexOf(':');
                    if (colonIndex > 0) {
                        String namespace = idString.substring(0, colonIndex);
                        return ResourceLocation.tryParse(namespace + ":block/" + path);
                    }
                }
            }
        }
        
        // Special case for bamboo
        if (woodType.equals("bamboo")) {
            return mcLoc("block/bamboo_block");
        }
        
        // Fallback: try common patterns
        String[] vanillaWoods = {"oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "mangrove", "cherry"};
        for (String vanilla : vanillaWoods) {
            if (woodType.equals(vanilla)) {
                return mcLoc("block/" + woodType + "_log");
            }
        }
        
        // Default fallback
        return mcLoc("block/oak_log");
    }
}

