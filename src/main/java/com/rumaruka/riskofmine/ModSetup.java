package com.rumaruka.riskofmine;

import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {


    public static CreativeModeTab ITEM_GROUP = new CreativeModeTab(MODID) {


        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ROMItems.ARMOR_PIERCING_ROUNDS);
        }
    };


}
