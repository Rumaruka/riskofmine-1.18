package com.rumaruka.riskofmine.client.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.rumaruka.riskofmine.common.entity.HealthOrbEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HealthOrbRenderer extends EntityRenderer<HealthOrbEntity> {

    private static final ResourceLocation HEAL_ORB_TEXTURES = new ResourceLocation("riskofmine:textures/entity/health_orb.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucentCull(HEAL_ORB_TEXTURES);

    public HealthOrbRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        this.shadowRadius = 0.15F;
        this.shadowStrength = 0.75F;
    }

    protected int getBlockLightLevel(HealthOrbEntity entityIn, BlockPos partialTicks) {
        return Mth.clamp(super.getBlockLightLevel(entityIn, partialTicks) + 7, 0, 15);
    }

    public void render(HealthOrbEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        int i = entityIn.getIcon();
        float f = (float) (i % 3 * 16) / 64.0F;
        float f1 = (float) (i % 3 * 16 + 16) / 64.0F;
        float f2 = (float) (i / 3 * 16) / 64.0F;
        float f3 = (float) (i / 3 * 16 + 16) / 64.0F;
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        float f7 = 255.0F;
        float f8 = ((float) entityIn.tickCount + partialTicks) / 2.0F;
        int j = (int) ((Mth.sin(f8 + 0.0F) + 1.0F) * 0.5F * 255.0F);
        int k = 255;
        int l = (int) ((Mth.sin(f8 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
        matrixStackIn.translate(0.0D, 0.1F, 0.0D);
        matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        float f9 = 0.3F;
        matrixStackIn.scale(0.3F, 0.3F, 0.3F);
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(RENDER_TYPE);
        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        vertex(ivertexbuilder, matrix4f, matrix3f, -0.5F, -0.25F, 255, j, l, f, f3, packedLightIn);
        vertex(ivertexbuilder, matrix4f, matrix3f, 0.5F, -0.25F, 255, j, l, f1, f3, packedLightIn);
        vertex(ivertexbuilder, matrix4f, matrix3f, 0.5F, 0.75F, 255, j, l, f1, f2, packedLightIn);
        vertex(ivertexbuilder, matrix4f, matrix3f, -0.5F, 0.75F, 255, j, l, f, f2, packedLightIn);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    private static void vertex(VertexConsumer bufferIn, Matrix4f matrixIn, Matrix3f matrixNormalIn, float x, float y, int red, int green, int blue, float texU, float texV, int packedLight) {
        bufferIn.vertex(matrixIn, x, y, 0.0F).color(red, green, blue, 128).uv(texU, texV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrixNormalIn, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public ResourceLocation getTextureLocation(HealthOrbEntity entityIn) {
        return HEAL_ORB_TEXTURES;
    }


}