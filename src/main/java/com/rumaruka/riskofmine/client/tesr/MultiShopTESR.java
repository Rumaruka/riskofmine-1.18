
package com.rumaruka.riskofmine.client.tesr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.rumaruka.riskofmine.common.tiles.MultiShopTE;
import com.rumaruka.riskofmine.init.ROMModels;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import ru.timeconqueror.timecore.animation.renderer.ModelConfiguration;
import ru.timeconqueror.timecore.api.client.render.model.TimeModelLoader;
import ru.timeconqueror.timecore.client.render.model.TimeModel;

import static com.rumaruka.riskofmine.RiskOfMine.rl;
import static com.rumaruka.riskofmine.RiskOfMine.tl;

public class MultiShopTESR implements BlockEntityRenderer<MultiShopTE> {
     public TimeModel modelOpen ;
//    public TimeModel modelClose = TimeModelLoader.loadJsonModel(rl("models/tile/multi_shop_close.json"), RenderType::entityCutout);

    public MultiShopTESR(BlockEntityRendererProvider.Context p_i226006_1_) {
        modelOpen= new TimeModel(ModelConfiguration.builder(ROMModels.MODEL_MULTI_SHOP).build());
    }

    @Override
    public void render(MultiShopTE multiShopTE, float ticks, PoseStack matrixStack, MultiBufferSource buffer, int light, int overlay) {
        ResourceLocation textureOpen = getMultiShopOpenTexture(multiShopTE);
//        ResourceLocation textureClose = getMultiShopCloseTexture(multiShopTE);
        matrixStack.pushPose();
        matrixStack.translate(0.5F, 0.01f, 0.5F);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
        matrixStack.scale(1.25f, 2f, 1.25f);
        modelOpen.renderToBuffer(matrixStack, buffer.getBuffer(modelOpen.renderType(textureOpen)), light, overlay, 1, 1, 1, 1);
        matrixStack.popPose();


    }

    private ResourceLocation getMultiShopOpenTexture(MultiShopTE tileEntityIn) {
        return tl("textures/tile/multi_shop_open.png").fullLocation();
    }

//    private ResourceLocation getMultiShopCloseTexture(MultiShopTE tileEntityIn) {
//        return rl("textures/tile/multi_shop_close.png");
//    }

}
