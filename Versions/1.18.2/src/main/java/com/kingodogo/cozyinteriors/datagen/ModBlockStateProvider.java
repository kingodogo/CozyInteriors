package com.kingodogo.cozyinteriors.datagen;

import com.kingodogo.cozyinteriors.CozyInteriors;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockStateProvider;

public class ModBlockStateProvider extends ForgeBlockStateProvider {
    public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CozyInteriors.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }
}

