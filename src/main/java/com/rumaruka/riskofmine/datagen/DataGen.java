package com.rumaruka.riskofmine.datagen;

import com.rumaruka.riskofmine.RiskOfMine;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = RiskOfMine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();

        if (event.includeServer()) {
            dataGenerator.addProvider(new ROMLootTableProvider(dataGenerator));
//            dataGenerator.addProvider(new RecipesGen(dataGenerator));
//
//            BlockTagsGen blockTagsGen = new BlockTagsGen(dataGenerator);
//
//            dataGenerator.addProvider(blockTagsGen);
//            dataGenerator.addProvider(new ItemTagsGen(dataGenerator, blockTagsGen));
//            dataGenerator.addProvider(new EntityTypeTagGen(dataGenerator));
        }
        if (event.includeClient()) {
//            dataGenerator.addProvider(new LangGen(dataGenerator));
        }
    }
}
