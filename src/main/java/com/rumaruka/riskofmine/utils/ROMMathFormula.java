package com.rumaruka.riskofmine.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class ROMMathFormula {

    private final Player player = Minecraft.getInstance().player;

    public static float powerIncreasing(float x, float y) {

        return Mth.abs((float) ROMMathUtils.multiply(x, y)) / 30 - (ROMMathUtils.percent(15) * ROMMathUtils.percent(x * y));


    }

    public static double speedIncreasing(float x) {

        return Math.abs(Math.tan(Math.PI * x / 180));


    }


}
