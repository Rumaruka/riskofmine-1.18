package com.rumaruka.riskofmine.init;

import net.minecraftforge.fml.common.Mod;
import ru.timeconqueror.timecore.api.registry.TimeModelRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;
import ru.timeconqueror.timecore.client.render.model.TimeModelLocation;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ROMModels {


    @AutoRegistrable
    private static final TimeModelRegister REGISTER = new TimeModelRegister(MODID);

    public static final TimeModelLocation MODEL_MULTI_SHOP = REGISTER.register("models/tile/multi_shop_open.json");
    public static final TimeModelLocation MODEL_SMALL_CHEST = REGISTER.register("models/tile/small_chest.json");


}
