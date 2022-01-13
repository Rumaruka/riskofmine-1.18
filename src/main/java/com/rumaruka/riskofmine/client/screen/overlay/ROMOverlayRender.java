package com.rumaruka.riskofmine.client.screen.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.lunar.data.Lunar;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;
import ru.timeconqueror.timecore.api.util.client.DrawHelper;

import java.awt.*;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID, value = Dist.CLIENT)
public class ROMOverlayRender {
    public static KeyMapping keyShowOverlay;
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void renderOverlays(RenderGameOverlayEvent.Post event) {


        if (event.getType() == RenderGameOverlayEvent.ElementType.CHAT) {
            if (keyShowOverlay.isDown()) {
                renderNearbyMoneyDisplay(event.getMatrixStack());
                renderNearbyLunarDisplay(event.getMatrixStack());

            }
        }
    }

    public static void keyPressed(FMLClientSetupEvent event) {
        keyShowOverlay = new KeyMapping("Show Overlay", GLFW.GLFW_KEY_M, "Risk of Mine");
        ClientRegistry.registerKeyBinding(keyShowOverlay);

    }


    private static void renderNearbyMoneyDisplay(PoseStack stack) {
        stack.pushPose();
        Player player = Minecraft.getInstance().player;
        Font fontRenderer = Minecraft.getInstance().font;
        if (!player.isDeadOrDying()) {
            ROMMoney romMoney = ROMMoney.from(player);
            Money money = romMoney.money;
            String toDisplay = getMoneyDisplay(money);
            Color color = Color.magenta;
//            mc.textureManager.bind();

            DrawHelper.drawString(stack, fontRenderer, toDisplay, 27.5f, 20, color.getRGB());


        }
        stack.popPose();
    }

    private static void renderNearbyLunarDisplay(PoseStack stack) {
        stack.pushPose();
        Player player = Minecraft.getInstance().player;
        Font fontRenderer = Minecraft.getInstance().font;
        if (!player.isDeadOrDying()) {
            ROMLunar romLunar = ROMLunar.from(player);
            Lunar lunar = romLunar.lunar;
            String toDisplay = getLunarDisplay(lunar);
            Color color = Color.magenta;
//            mc.textureManager.bind();
//            AbstractGui.blit(stack,1,1,27.5f,32,1,1,1,1);
            DrawHelper.drawString(stack, fontRenderer, toDisplay, 27.5f, 30, color.getRGB());


        }
        stack.popPose();
    }


    private static String getLunarDisplay(Lunar lunar) {
        float currentLunar = lunar.getCurrentLunar();
        return I18n.get("riskofmine.currentlunar.name") + currentLunar;

    }

    private static String getMoneyDisplay(Money money) {
        float currentMoney = money.getCurrentMoney();
        return I18n.get("riskofmine.currentmoney.name") + currentMoney;

    }
}
