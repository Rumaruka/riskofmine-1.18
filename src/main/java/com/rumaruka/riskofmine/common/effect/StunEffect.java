package com.rumaruka.riskofmine.common.effect;

import com.google.common.collect.Maps;
import com.rumaruka.riskofmine.common.config.ROMConfig;
import com.rumaruka.riskofmine.init.ROMEffects;
import com.rumaruka.riskofmine.utils.ROMUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;


public class StunEffect extends MobEffect {
    private final Map<Attribute, AttributeModifier> attributeModifiers = Maps.newHashMap();

    public StunEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {

        if (this == ROMEffects.STUN.get()) {
            if (entityLivingBaseIn instanceof LivingEntity || entityLivingBaseIn instanceof ServerPlayer) {
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 9, true, false));
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 4, true, false));
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 4, true, false));
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 5, true, false));
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.CONFUSION, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 5, true, false));
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.GLOWING, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 5, true, false));

            }


        }


        super.applyEffectTick(entityLivingBaseIn, amplifier);
    }

    @Override
    public String getDescriptionId() {
        return super.getDescriptionId();
    }

    @Override
    public MobEffect addAttributeModifier(Attribute attributeIn, String uuid, double amount, AttributeModifier.Operation operation) {
        AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(uuid), this::getDescriptionId, amount, operation);
        this.attributeModifiers.put(attributeIn, attributemodifier);
        return this;
    }

    public Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return this.attributeModifiers;
    }

    public void removeAttributeModifiers(LivingEntity entityLivingBaseIn, AttributeMap attributeMapIn, int amplifier) {
        for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            AttributeInstance modifiableattributeinstance = attributeMapIn.getInstance(entry.getKey());
            if (modifiableattributeinstance != null) {
                modifiableattributeinstance.removeModifier(entry.getValue());
            }
        }

    }

    public void addAttributeModifiers(LivingEntity entityLivingBaseIn, AttributeMap attributeMapIn, int amplifier) {
        for (Map.Entry<Attribute, AttributeModifier> entry : this.attributeModifiers.entrySet()) {
            AttributeInstance modifiableattributeinstance = attributeMapIn.getInstance(entry.getKey());
            if (modifiableattributeinstance != null) {
                AttributeModifier attributemodifier = entry.getValue();
                modifiableattributeinstance.removeModifier(attributemodifier);
                modifiableattributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), this.getDescriptionId() + " " + amplifier, this.getAttributeModifierValue(amplifier, attributemodifier), attributemodifier.getOperation()));
            }
        }

    }

    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return modifier.getAmount() * (double) (amplifier + 1);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {

        if (this == ROMEffects.STUN.get()) {
            if (entityLivingBaseIn instanceof ServerPlayer) {
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 9, true, false));
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 4, true, false));
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 4, true, false));
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 5, true, false));
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.CONFUSION, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 5, true, false));
                entityLivingBaseIn.addEffect(new MobEffectInstance(MobEffects.GLOWING, ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get()), 5, true, false));


            }
        }

        super.applyInstantenousEffect(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }


    public MobEffect getEffect() {
        return ROMEffects.STUN.get();
    }


}
