package com.kingodogo.cozyinteriors.datagen;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.compat.WoodTypeDetector;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Set;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen, String locale) {
        super(gen, CozyInteriors.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        // Add creative tab name
        add("itemGroup.cozyinteriors", "CozyInteriors");
        
        // Add translations for all detected wood types
        Set<String> woodTypes = WoodTypeDetector.detectWoodTypes();
        for (String woodType : woodTypes) {
            String chairName = woodType + "_chair";
            String displayName = formatWoodTypeName(woodType) + " Chair";
            add("block.cozyinteriors." + chairName, displayName);
        }
    }
    
    private String formatWoodTypeName(String woodType) {
        // Convert "dark_oak" -> "Dark Oak", "oak" -> "Oak", etc.
        String[] parts = woodType.split("_");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) result.append(" ");
            String part = parts[i];
            if (part.length() > 0) {
                result.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) {
                    result.append(part.substring(1));
                }
            }
        }
        return result.toString();
    }
}

