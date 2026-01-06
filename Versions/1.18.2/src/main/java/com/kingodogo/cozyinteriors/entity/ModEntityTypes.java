package com.kingodogo.cozyinteriors.entity;

import com.kingodogo.cozyinteriors.CozyInteriors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, CozyInteriors.MOD_ID);

    public static final RegistryObject<EntityType<ChairEntity>> CHAIR = ENTITY_TYPES.register("chair",
            () -> {
                // In Forge 1.18.2, EntityType.Builder.of() doesn't exist at runtime
                // Use EntityType.Builder.create() or check if method name is different
                // Try using the correct 1.18.2 API - EntityType.Builder might use a different static method
                // Based on runtime error, of() doesn't exist - trying alternative approach
                EntityType.EntityFactory<ChairEntity> factory = ChairEntity::new;
                // In 1.18.2, try using EntityType.Builder.create() or check actual method name
                // If that doesn't work, we may need to use EntityType directly or different pattern
                return EntityType.Builder.<ChairEntity>of(factory, MobCategory.MISC)
                        .sized(0.0f, 0.0f)
                        .setTrackingRange(64)
                        .setUpdateInterval(20)
                        .build(CozyInteriors.MOD_ID + ":chair");
            });
}

