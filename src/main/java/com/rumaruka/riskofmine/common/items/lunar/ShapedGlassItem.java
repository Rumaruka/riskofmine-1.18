package com.rumaruka.riskofmine.common.items.lunar;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ShapedGlassItem extends ItemCollectiblesBase {

    public final UUID healthModifierID = UUID.fromString("208b4d4c-50ef-4b45-a097-4bed633cdbff");

    public ShapedGlassItem() {
        super(EnumType.LUNAR, CategoryEnum.DAMAGE, 1);


    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TextComponent("ror.alt.info"));
        if (Screen.hasAltDown()) {

            tooltip.add(new TextComponent("riskofmine.rarity" + ":"));
            tooltip.add(new TextComponent((getColor() + getTypeName())));
            tooltip.add(new TextComponent("riskofmine.category" + ":"));
            tooltip.add(new TextComponent((getColors() + getCategoryName())));
        }
        tooltip.add(new TextComponent("ror.shiftpress.info"));
        if (Screen.hasShiftDown()) {
            tooltip.add(new TextComponent("ror.sg.info"));
        }
    }


    @Override
    public void inventoryTick(ItemStack p_77663_1_, Level p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {


        if (!p_77663_2_.isClientSide()) {
            Player playerEntity = (Player) p_77663_3_;
            if (playerEntity.getOffhandItem().getItem() == this) {
                playerEntity.getAttributes().addTransientAttributeModifiers(this.getAttributeModifiers(EquipmentSlot.OFFHAND, p_77663_1_));

            }

        }


        super.inventoryTick(p_77663_1_, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        final Multimap<Attribute, AttributeModifier> defaultModifiers;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (slot == EquipmentSlot.OFFHAND) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modificator", 64, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(healthModifierID, "Health Minus", -16, AttributeModifier.Operation.ADDITION));
        }
        defaultModifiers = builder.build();
        return defaultModifiers;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (!livingEntity.level.isClientSide()) {
            Player playerEntity = (Player) livingEntity;
            playerEntity.getAttributes().addTransientAttributeModifiers(this.getAttributeModifiers(identifier, stack));
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(String identifier, ItemStack stack) {
        final Multimap<Attribute, AttributeModifier> defaultModifiers;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modificator", 64, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.MAX_HEALTH, new AttributeModifier(healthModifierID, "Health Minus", -16, AttributeModifier.Operation.ADDITION));

        defaultModifiers = builder.build();
        return defaultModifiers;
    }

    @Override
    public boolean showAttributesTooltip(String identifier, ItemStack stack) {
        return false;
    }
}
