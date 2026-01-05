package com.kingodogo.cozyinteriors.items;

import com.kingodogo.cozyinteriors.CozyInteriors;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModCreativeModeTab {
    public static final CreativeModeTab COZY_INTERIORS_TAB = new CreativeModeTab(CozyInteriors.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.OAK_PLANKS);
        }
    };
}

