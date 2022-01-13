package com.rumaruka.riskofmine.utils;


import com.rumaruka.riskofmine.client.fx.FocusCrystalFX;
import com.rumaruka.riskofmine.client.fx.StunFX;
import com.rumaruka.riskofmine.init.ROMParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleUtils {


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticle(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ROMParticles.FOCUS_CRYSTAL.get(), FocusCrystalFX.Factory::new);
        Minecraft.getInstance().particleEngine.register(ROMParticles.STUN_PARTICLES.get(), StunFX.Factory::new);
    }
}
