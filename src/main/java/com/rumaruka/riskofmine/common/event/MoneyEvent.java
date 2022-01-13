package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID)
public class MoneyEvent {

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {

        if (event.getSource().getEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getSource().getEntity();
            LivingEntity livingEntity = event.getEntityLiving();
            Level level = livingEntity.level;
            ROMMoney romMoney = ROMMoney.from(player);
            if (!level.isClientSide) {
                if (player != null) {
                    Money money = romMoney.money;
                    money.addMoney(player, 10.0f);
                    romMoney.detectAndSendChanges();
                }


            }

        }

        if (event.getSource().getEntity() instanceof AmbientCreature && event.getEntityLiving() instanceof ServerPlayer) {
            AmbientCreature livingEntity = (AmbientCreature) event.getSource().getEntity();
            ServerPlayer player = (ServerPlayer) event.getEntityLiving();
            Level world = player.level;
            ROMMoney romMoney = ROMMoney.from(player);
            if (!world.isClientSide) {
                if (event.getSource().isBypassInvul()) {
                    return;
                } else {
                    if (livingEntity != null) {
                        Money money = romMoney.money;
                        money.setMoney(0.0f);
                        romMoney.detectAndSendChanges();

                    }
                }


            }
        }

    }
}
