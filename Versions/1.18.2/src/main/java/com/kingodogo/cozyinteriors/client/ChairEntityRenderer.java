package com.kingodogo.cozyinteriors.client;

import com.kingodogo.cozyinteriors.entity.ChairEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ChairEntityRenderer extends EntityRenderer<ChairEntity> {
    public ChairEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ChairEntity entity) {
        return null; // No texture needed - entity is invisible
    }

    // Don't render anything - the entity is invisible
    @Override
    public boolean shouldRender(ChairEntity entity, net.minecraft.client.renderer.culling.Frustum camera, double camX, double camY, double camZ) {
        return false; // Never render the entity itself
    }
}

