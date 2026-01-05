package com.kingodogo.cozyinteriors.datagen;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Set;
import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.recipes.RecipeProvider implements IConditionBuilder {
    public RecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        Set<String> woodTypes = WoodTypeDetector.detectWoodTypes();
        
        for (String woodType : woodTypes) {
            ItemLike planks = getPlanksForWood(woodType);
            ResourceLocation chairId = ResourceLocation.tryParse(CozyInteriors.MOD_ID + ":" + woodType + "_chair");
            
            ShapedRecipeBuilder.shaped(net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(chairId), 1)
                .pattern("S  ")
                .pattern("PPS")
                .pattern("PPP")
                .define('S', planks)
                .define('P', planks)
                .unlockedBy("has_planks", has(planks))
                .save(consumer, chairId);
        }
    }

    private ItemLike getPlanksForWood(String woodType) {
        // Note: mangrove, cherry, and bamboo planks don't exist in Minecraft 1.18.2
        // They were added in 1.19+. Using oak as fallback for these.
        return switch (woodType) {
            case "oak" -> Items.OAK_PLANKS;
            case "spruce" -> Items.SPRUCE_PLANKS;
            case "birch" -> Items.BIRCH_PLANKS;
            case "jungle" -> Items.JUNGLE_PLANKS;
            case "acacia" -> Items.ACACIA_PLANKS;
            case "dark_oak" -> Items.DARK_OAK_PLANKS;
            case "mangrove", "cherry", "bamboo" -> Items.OAK_PLANKS; // Fallback for 1.19+ wood types
            default -> Items.OAK_PLANKS;
        };
    }
}

