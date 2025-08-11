package com.model.tank.client.render.tank;

import com.model.tank.api.client.render.ModularEntityRender;
import com.model.tank.api.entity.ModularEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;

public class TankRender extends ModularEntityRender {
    public static final String BODY_BONE = "body";
    public TankRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TankModel());
    }

    @Override
    public void preRender(PoseStack poseStack, ModularEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        GeoBone bone = model.getBone("root").get();
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
