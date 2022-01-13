package com.rumaruka.riskofmine.init;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;
import static com.rumaruka.riskofmine.RiskOfMine.rl;


public class ROMSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> ROM_CHEST_OPEN = REGISTER.register("riskofmine.block.open_chest",
            () -> new SoundEvent(rl("riskofmine.block.open_chest")));
    public static final RegistryObject<SoundEvent> ROM_CHEST_NOT_MONEY = REGISTER.register("riskofmine.block.not_money",
            () -> new SoundEvent(rl("riskofmine.block.not_money")));

    public static final RegistryObject<SoundEvent> ROM_PLAYER_LEVEL_UP = REGISTER.register("riskofmine.player.level_up",
            () -> new SoundEvent(rl("riskofmine.player.level_up")));


}