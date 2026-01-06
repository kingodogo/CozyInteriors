package com.kingodogo.cozyinteriors.items;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.blocks.ModBlocks;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModCreativeModeTab {
    public static final CreativeModeTab COZY_INTERIORS_TAB = new CreativeModeTab(CozyInteriors.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.OAK_PLANKS);
        }
        
        @Override
        public void fillItemList(NonNullList<ItemStack> items) {
            // Manually add all chair items to the creative tab
            // This ensures they appear even if .tab() doesn't work properly
            String[] woodTypes = {"oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "bamboo"};
            for (String woodType : woodTypes) {
                var chairItem = ModBlocks.CHAIR_ITEMS.get(woodType + "_chair");
                if (chairItem != null) {
                    try {
                        if (chairItem.isPresent()) {
                            ItemStack stack = new ItemStack(chairItem.get());
                            if (!stack.isEmpty()) {
                                items.add(stack);
                            }
                        }
                    } catch (Exception e) {
                        // Item not ready yet, skip
                    }
                }
            }
        }
    };
}

