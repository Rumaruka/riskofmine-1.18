package com.rumaruka.riskofmine.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ShieldEffect extends MobEffect {
    protected ShieldEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public MobEffect addAttributeModifier(Attribute attributeIn, String uuid, double amount, AttributeModifier.Operation operation) {
        return super.addAttributeModifier(attributeIn, uuid, amount, operation);
    }
}
