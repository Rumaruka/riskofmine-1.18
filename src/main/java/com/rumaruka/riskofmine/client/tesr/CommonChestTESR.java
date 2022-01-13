package com.rumaruka.riskofmine.client.tesr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.rumaruka.riskofmine.common.tiles.CommonChestTE;
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

public class CommonChestTESR implements BlockEntityRenderer<CommonChestTE> {
    public TimeModel model ;

    public CommonChestTESR(BlockEntityRendererProvider.Context rendererDispatcherIn) {
            model= new TimeModel(ModelConfiguration.builder(ROMModels.MODEL_SMALL_CHEST).build());
    }

    @Override
    public void render(CommonChestTE tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ResourceLocation texture = getTexture(tileEntityIn);
       // RenderType renderType = this.model.renderType(texture);
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5F, 0.01f, 0.5F);
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(180));
        matrixStackIn.scale(1.15f, 1f, 1.25f);
    //    model.renderToBuffer(matrixStackIn, bufferIn.getBuffer(renderType), combinedLightIn, combinedOverlayIn, 1, 1, 1, 1);

        matrixStackIn.popPose();
    }


    private ResourceLocation getTexture(CommonChestTE tileEntityIn) {
        return rl("textures/tile/small_chest.png");
    }

}
