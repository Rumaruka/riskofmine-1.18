package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.common.cap.lunar.ILunar;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.money.IMoney;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import ru.timeconqueror.timecore.TimeCore;
import ru.timeconqueror.timecore.api.registry.CapabilityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import ru.timeconqueror.timecore.common.capability.owner.CapabilityOwner;

import static ru.timeconqueror.timecore.api.util.Hacks.promise;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ROMCapability {

    @AutoRegistrable
    private static final CapabilityRegister REGISTER = new CapabilityRegister(RiskOfMine.MODID);

    public static final Capability<ROMMoney> MONEY = promise();

    public static final Capability<ROMLunar> LUNAR = promise();
    @AutoRegistrable.Init
    private static void register() {

        REGISTER.register(IMoney.class);
        REGISTER.register(ILunar.class);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            TimeCore.INSTANCE.getCapabilityManager().attachStaticCoffeeCapability(CapabilityOwner.ENTITY, MONEY, entity -> entity instanceof Player, entity -> new ROMMoney((Player) entity));
            TimeCore.INSTANCE.getCapabilityManager().enableSyncingPlayerCapabilityOnJoin(entity -> {
                ROMMoney cap = ROMMoney.from(entity);
                if (cap != null) cap.sendAllData();
            });
        });

        event.enqueueWork(()->{
           TimeCore.INSTANCE.getCapabilityManager().attachStaticCoffeeCapability(CapabilityOwner.ENTITY,LUNAR,entity -> entity instanceof Player, entity -> new ROMLunar((Player) entity));
            TimeCore.INSTANCE.getCapabilityManager().enableSyncingPlayerCapabilityOnJoin(entity -> {
                ROMLunar cap = ROMLunar.from(entity);
                if (cap != null) cap.sendAllData();
            });
        });
    }
}
