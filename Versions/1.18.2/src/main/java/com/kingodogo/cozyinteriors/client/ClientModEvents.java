package com.kingodogo.cozyinteriors.client;

import com.kingodogo.cozyinteriors.CozyInteriors;
import com.kingodogo.cozyinteriors.blocks.ModBlocks;
import com.kingodogo.cozyinteriors.entity.ModEntityTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CozyInteriors.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Register entity renderer for ChairEntity (invisible)
        event.enqueueWork(() -> {
            EntityRenderers.register(ModEntityTypes.CHAIR.get(), ChairEntityRenderer::new);
        });
        
        // Set all chair blocks to use cutout render type (fixes texture glitches)
        event.enqueueWork(() -> {
            ModBlocks.CHAIR_BLOCKS.values().forEach(blockRegistryObject -> {
                if (blockRegistryObject.isPresent()) {
                    ItemBlockRenderTypes.setRenderLayer(blockRegistryObject.get(), RenderType.cutout());
                }
            });
        });
    }
}
