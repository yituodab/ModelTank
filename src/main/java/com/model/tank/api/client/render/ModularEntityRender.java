package com.model.tank.api.client.render;

import com.model.tank.api.entity.ModularEntity;
import com.model.tank.resource.DataLoader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.object.Color;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ModularEntityRender extends GeoEntityRenderer<ModularEntity> {
    public ModularEntityRender(EntityRendererProvider.Context renderManager, GeoModel model) {
        super(renderManager, model);
    }

    @Override
    public void render(@NotNull ModularEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public void defaultRender(PoseStack poseStack, ModularEntity animatable, MultiBufferSource bufferSource, @Nullable RenderType renderType, @Nullable VertexConsumer buffer, float yaw, float partialTick, int packedLight) {
        poseStack.pushPose();
        Color renderColor = this.getRenderColor(animatable, partialTick, packedLight);
        float red = renderColor.getRedFloat();
        float green = renderColor.getGreenFloat();
        float blue = renderColor.getBlueFloat();
        float alpha = renderColor.getAlphaFloat();
        int packedOverlay = this.getPackedOverlay(animatable, 0.0F, partialTick);
        ResourceLocation resource = this.getGeoModel().getModelResource(animatable);
        Model gotModel = DataLoader.getModel(resource);
        BakedGeoModel model = gotModel == null ? this.getGeoModel().getBakedModel(resource) : BakedModelFactory.getForNamespace(resource.getNamespace()).constructGeoModel(GeometryTree.fromModel(gotModel));
        if (renderType == null) {
            renderType = this.getRenderType(animatable, this.getTextureLocation(animatable), bufferSource, partialTick);
        }
        if (buffer == null) {
            buffer = bufferSource.getBuffer(renderType);
        }

        this.preRender(poseStack, animatable, model, bufferSource, buffer, false, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if (this.firePreRenderEvent(poseStack, model, bufferSource, partialTick, packedLight)) {
            this.preApplyRenderLayers(poseStack, animatable, model, renderType, bufferSource, buffer, (float)packedLight, packedLight, packedOverlay);
            this.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, false, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
            this.applyRenderLayers(poseStack, animatable, model, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
            this.postRender(poseStack, animatable, model, bufferSource, buffer, false, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
            this.firePostRenderEvent(poseStack, model, bufferSource, partialTick, packedLight);
        }

        poseStack.popPose();
        this.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        this.doPostRenderCleanup();
    }
}
