package com.kingodogo.cozyinteriors.datagen;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Set;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CozyInteriors.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<String> woodTypes = WoodTypeDetector.detectWoodTypes();
        
        for (String woodType : woodTypes) {
            String chairName = woodType + "_chair";
            withExistingParent("item/chairs/" + chairName, modLoc("block/chairs/" + chairName));
        }
    }
}

