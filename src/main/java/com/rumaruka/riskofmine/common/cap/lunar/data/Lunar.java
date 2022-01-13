package com.rumaruka.riskofmine.common.cap.lunar.data;


import net.minecraft.world.entity.player.Player;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;

public class Lunar extends PropertyContainer {
    public final CoffeeProperty<Float> currentLunar = prop("current_lunar", 0F).synced();
    public final CoffeeProperty<Float> burnoutTime = prop("burnout", 0F).synced();
    public final CoffeeProperty<Integer> timeout = prop("timeout", 0);

    public static float getMaxLunar(Player player) {
        return Float.MAX_VALUE;
    }

    public float getCurrentLunar() {
        return currentLunar.get();
    }

    public void setBurnout(float value) {
        burnoutTime.set(Math.max(value, 0.0F));
    }

    public void setTimeout(int value) {
        timeout.set(value);
    }

    public float getBurnout() {
        return burnoutTime.get();
    }

    public int getTimeout() {
        return timeout.get();
    }

    public void setLunar(float value) {
        if (getCurrentLunar() != value) {
            currentLunar.set(value);
        }
    }

    public boolean isRegenReady() {
        if (getTimeout() <= 0) return getBurnout() <= 0;
        setTimeout(getTimeout() - 1);
        return false;
    }

    public void addLunar(Player player, float add) {
        setLunar(Math.min(getCurrentLunar() + add, getMaxLunar(player)));
    }

    public boolean consumeLunar(Player player, float price) {
        if (!player.isCreative()) {
            if (hasLunar(price)) {
                setLunar(getCurrentLunar() - price);
                setTimeout(20);
                return true;
            }

            return false;
        }
        return true;
    }

    public boolean hasLunar(float price) {
        return getCurrentLunar() >= price;
    }

    public boolean checkMoneyEitherSide(boolean client, Player player, float price) {
        if (client) {
            return player.isCreative() || hasLunar(price);
        }
        return consumeLunar(player, price);
    }
}
