package com.model.tank.entities.tank;

import com.model.tank.api.client.entity.ModEntityRender;
import com.model.tank.resource.DataManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TankRender extends ModEntityRender {
    public TankRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TankModel());
    }
}
