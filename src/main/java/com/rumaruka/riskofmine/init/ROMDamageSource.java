package com.rumaruka.riskofmine.init;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;

import javax.annotation.Nullable;

public class ROMDamageSource {

    public static final DamageSource BLEED = (new DamageSource("bleed"));


    public static DamageSource bleeded(FireworkRocketEntity fireworkRocketEntity_, @Nullable Entity entity_) {
        return (new IndirectEntityDamageSource("bleeded", fireworkRocketEntity_, entity_)).setScalesWithDifficulty();
    }

}
