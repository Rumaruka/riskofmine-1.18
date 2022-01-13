package com.rumaruka.riskofmine.common.cap.money.data;

import net.minecraft.world.entity.player.Player;
import ru.timeconqueror.timecore.common.capability.property.CoffeeProperty;
import ru.timeconqueror.timecore.common.capability.property.container.PropertyContainer;


public class Money extends PropertyContainer {
    public final CoffeeProperty<Float> currentMoney = prop("current_money", 0F).synced();
    public final CoffeeProperty<Float> burnoutTime = prop("burnout", 0F).synced();
    public final CoffeeProperty<Integer> timeout = prop("timeout", 0);

    public static float getMaxMoney(Player player) {
        return Float.MAX_VALUE;
    }

    public float getCurrentMoney() {
        return currentMoney.get();
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

    public void setMoney(float value) {
        if (getCurrentMoney() != value) {
            currentMoney.set(value);
        }
    }

    public boolean isRegenReady() {
        if (getTimeout() <= 0) return getBurnout() <= 0;
        setTimeout(getTimeout() - 1);
        return false;
    }

    public void addMoney(Player player, float add) {
        setMoney(Math.min(getCurrentMoney() + add, getMaxMoney(player)));
    }

    public boolean consumeMoney(Player player, float price) {
        if (!player.isCreative()) {
            if (hasMoney(price)) {
                setMoney(getCurrentMoney() - price);
                setTimeout(20);
                return true;
            }

            return false;
        }
        return true;
    }

    public boolean hasMoney(float price) {
        return getCurrentMoney() >= price;
    }

    public boolean checkMoneyEitherSide(boolean client, Player player, float price) {
        if (client) {
            return player.isCreative() || hasMoney(price);
        }
        return consumeMoney(player, price);
    }

//    public EntityGoldenIngotBullets createBullets(Level level, Money money, Player player){
//        EntityGoldenIngotBullets bullets = new EntityGoldenIngotBullets(level,player);
//        return bullets;
//    }
}
