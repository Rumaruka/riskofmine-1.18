package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.common.effect.BleedEffect;
import com.rumaruka.riskofmine.common.effect.StunEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;

public class ROMEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);
    /**
     * POTIONS FOR TESTING EFFECTS!
     */
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, MODID);


    public static final RegistryObject<MobEffect> STUN = EFFECTS.register("stun", () -> new StunEffect(MobEffectCategory.HARMFUL, 0x2A2D2E));
    public static final RegistryObject<MobEffect> BLEED = EFFECTS.register("bleed", () -> new BleedEffect(MobEffectCategory.BENEFICIAL, 5646433));

}
