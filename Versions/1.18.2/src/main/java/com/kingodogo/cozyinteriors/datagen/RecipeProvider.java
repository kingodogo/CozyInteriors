package com.kingodogo.cozyinteriors.datagen;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
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
            ResourceLocation chairId = new ResourceLocation(CozyInteriors.MOD_ID, woodType + "_chair");
            
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
        return switch (woodType) {
            case "oak" -> Items.OAK_PLANKS;
            case "spruce" -> Items.SPRUCE_PLANKS;
            case "birch" -> Items.BIRCH_PLANKS;
            case "jungle" -> Items.JUNGLE_PLANKS;
            case "acacia" -> Items.ACACIA_PLANKS;
            case "dark_oak" -> Items.DARK_OAK_PLANKS;
            case "mangrove" -> Items.MANGROVE_PLANKS;
            case "cherry" -> Items.CHERRY_PLANKS;
            case "bamboo" -> Items.BAMBOO_PLANKS;
            default -> Items.OAK_PLANKS;
        };
    }
}

