package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.lunar.data.Lunar;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID)
public class LunarEvent {
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {

        if (event.getSource().getEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getSource().getEntity();
            LivingEntity livingEntity = event.getEntityLiving();
            Level level = livingEntity.level;

            ROMLunar romLunar = ROMLunar.from(player);
            if (!level.isClientSide) {
                if (player != null) {
                    Lunar lunar = romLunar.lunar;

                    if (livingEntity.tickCount % 10 == 0) {
                        ItemEntity itemEntity = new ItemEntity(level, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), new ItemStack(ROMItems.LUNAR_COIN));
                        level.addFreshEntity(itemEntity);
                        romLunar.detectAndSendChanges();

                    }


                }


            }

        }
        if (event.getSource().getEntity() instanceof AmbientCreature && event.getEntityLiving() instanceof ServerPlayer) {
            AmbientCreature livingEntity = (AmbientCreature) event.getSource().getEntity();
            ServerPlayer player = (ServerPlayer) event.getEntityLiving();
            Level world = player.level;
            ROMLunar romLunar = ROMLunar.from(player);
            if (!world.isClientSide) {
                if (event.getSource().isBypassInvul()) {
                    return;
                } else {
                    if (livingEntity != null) {
                        Lunar lunar = romLunar.lunar;
                        lunar.setLunar(0.0f);
                        romLunar.detectAndSendChanges();

                    }
                }


            }
        }
    }
}
