package com.rumaruka.riskofmine.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerMonsterTooth extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {


    public LayerMonsterTooth(LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> p_i50926_1_) {
        super(p_i50926_1_);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int p_225628_3_, AbstractClientPlayer playerEntity, float swing, float swingAmount, float pTicks, float aTicks, float yaw, float scale) {
        for (int i = 0; i < playerEntity.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = playerEntity.getInventory().getItem(i);
            if (itemStack.getItem() == ROMItems.MONSTER_TOOTH) {
                matrixStack.pushPose();
                matrixStack.translate(0.0F, 0.0F, 0.125F);
                double d0 = Mth.lerp((double) pTicks, playerEntity.xCloakO, playerEntity.xCloak) - Mth.lerp((double) pTicks, playerEntity.xo, playerEntity.getX());
                double d1 = Mth.lerp((double) pTicks, playerEntity.yCloakO, playerEntity.yCloak) - Mth.lerp((double) pTicks, playerEntity.yo, playerEntity.getY());
                double d2 = Mth.lerp((double) pTicks, playerEntity.zCloakO, playerEntity.zCloak) - Mth.lerp((double) pTicks, playerEntity.zo, playerEntity.getZ());
                float f = playerEntity.yBodyRotO + (playerEntity.yBodyRot - playerEntity.yBodyRotO);
                double d3 = (double) Mth.sin(f * ((float) Math.PI / 180F));
                double d4 = (double) (-Mth.cos(f * ((float) Math.PI / 180F)));
                float f1 = (float) d1 * 10.0F;
                f1 = Mth.clamp(f1, -.0F, 32.0F);
                float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
                f2 = Mth.clamp(f2, 0.0F, 150.0F);
                float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;
                f3 = Mth.clamp(f3, 20.0F, 20.0F);
                if (f2 < 0.0F) {
                    f2 = 0.0F;
                }

                float f4 = Mth.lerp(pTicks, playerEntity.oBob, playerEntity.bob);
                f1 = f1 + Mth.sin(Mth.lerp(pTicks, playerEntity.walkDistO, playerEntity.walkDist) * 6.0F) * 32.0F * f4;
                if (playerEntity.isCrouching()) {
                    f1 += 25.0F;
                }

                matrixStack.mulPose(Vector3f.XP.rotationDegrees(6.0F + f2 / 2.0F + f1));
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(f3 / 2.0F));
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - f3 / 2.0F));
                Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.FIXED, p_225628_3_, OverlayTexture.NO_OVERLAY, matrixStack, buffer, 0);

                matrixStack.popPose();

            }
        }
    }
}
