package com.rumaruka.riskofmine.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber
public class MovingHandler {
    private static final HashMap<UUID, MoveInfo> moveMap = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerIn(PlayerEvent.PlayerLoggedInEvent e) {
        moveMap.put(e.getPlayer().getUUID(), new MoveInfo(e.getPlayer()));

    }


    @SubscribeEvent
    public static void onPlayerOut(PlayerEvent.PlayerLoggedOutEvent e) {
        moveMap.remove(e.getPlayer().getUUID());

    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            MoveInfo moveInfo = moveMap.get(event.player.getUUID());
            if (moveInfo != null) {
                moveInfo.update(event.player);
            }
        }

    }

    public static boolean isMoving(ServerPlayer mp) {
        MoveInfo moveInfo = moveMap.get(mp.getUUID());
        if (moveInfo != null) {
            return moveInfo.isPosChanged();
        }
        return false;
    }

    public static class MoveInfo {
        private double lastPosX;
        private double lastPosY;
        private double lastPosZ;

        private boolean posChanged;

        public MoveInfo(Player player) {
            updateLastPos(player);
        }

        private void updateLastPos(Player player) {
            lastPosX = player.getX();
            lastPosY = player.getY();
            lastPosZ = player.getZ();
        }

        public void update(Player player) {
            posChanged = lastPosX != player.position().x() || lastPosY != player.position().y() || lastPosZ != player.position().z();
            if (posChanged) updateLastPos(player);

        }

        public boolean isPosChanged() {
            return posChanged;
        }

    }
}
