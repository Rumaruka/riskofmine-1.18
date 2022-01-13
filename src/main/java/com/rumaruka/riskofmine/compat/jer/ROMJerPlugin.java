package com.rumaruka.riskofmine.compat.jer;

import com.rumaruka.riskofmine.datagen.loot.ROMLootTables;
import jeresources.api.IDungeonRegistry;
import jeresources.api.IJERAPI;
import jeresources.api.IMobRegistry;
import jeresources.api.JERPlugin;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ROMJerPlugin {
    @JERPlugin
    public static IJERAPI jerAPI;

    public static void setup(FMLCommonSetupEvent e){
        initDungeonLoot();
    }

    private static void initDungeonLoot() {
        IDungeonRegistry dungeonRegistry = jerAPI.getDungeonRegistry();
        dungeonRegistry.registerChest("RiskOfMine:Small Chest", ROMLootTables.SMALL_CHEST);
        dungeonRegistry.registerChest("RiskOfMine:Large Chest", ROMLootTables.LARGE_CHEST);
        dungeonRegistry.registerChest("RiskOfMine:Lunar Chest", ROMLootTables.LUNAR_CHEST);
    }


}
