package com.rumaruka.riskofmine.common.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ROMConfig {

    public static final ForgeConfigSpec commonConfig;
    public static final General COMMON;
    static {


        final Pair<General, ForgeConfigSpec> commonConfigPair = new ForgeConfigSpec.Builder().configure(General::new);
        commonConfig = commonConfigPair.getRight();
        COMMON = commonConfigPair.getLeft();
    }
    //Add parameters
    public static class General {
        public static ForgeConfigSpec.IntValue sizeCurio;
        public static ForgeConfigSpec.IntValue cooldownEq;
        public static ForgeConfigSpec.IntValue durStunConfig;
        public static ForgeConfigSpec.IntValue durBleedConfig;
        public static ForgeConfigSpec.IntValue priceSmallChest;
        public static ForgeConfigSpec.IntValue priceLargeChest;
        public static ForgeConfigSpec.IntValue priceLegendaryChest;
        public static ForgeConfigSpec.BooleanValue isBossBar;

        General(ForgeConfigSpec.Builder builder){
            builder.push("Risk of Mine General");
            builder.comment("Curio Size Slot."+"NEED RESTART GAME!");
            sizeCurio = builder.defineInRange("Slot size",4,1,9);
            builder.comment("Cooldowns Equipment Items");
            cooldownEq = builder.defineInRange("Cooldowns",5000,1000,Integer.MAX_VALUE);
            builder.comment("Duration Stun");
            durStunConfig = builder.defineInRange("Duration Stun:",6000,1000,Integer.MAX_VALUE);
            builder.comment("Duration Bleed");
            durBleedConfig = builder.defineInRange("Duration Bleed:",100,10,Integer.MAX_VALUE);
            builder.comment("Payment for Open Small Chest");
            priceSmallChest = builder.defineInRange("Payment Small Chest:",10,10,Integer.MAX_VALUE);
            builder.comment("Payment for Open Large Chest");
            priceLargeChest = builder.defineInRange("Payment Large Chest:",100,10,Integer.MAX_VALUE);
            builder.comment("Payment for Open Legendary Chest");
            priceLegendaryChest = builder.defineInRange("Payment Legendary Chest:",1000,10,Integer.MAX_VALUE);
            builder.comment("Boss bar enable?");
            isBossBar = builder.define("Enable:",false);
            builder.pop();
        }

    }

}
