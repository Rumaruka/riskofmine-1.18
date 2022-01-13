package com.rumaruka.riskofmine.common.items.equipment;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Predicate;

public abstract class EquipmentShootItemBase extends ItemCollectiblesBase {
    private final CategoryEnum categoryEnum;
    public static final Predicate<ItemStack> GOLDEN_INGOT = (predicate) -> {
        return predicate.getItem() == Items.GOLD_INGOT;
    };

    public EquipmentShootItemBase(CategoryEnum categoryEnum) {
        super(EnumType.EQUIPMENT, categoryEnum, 1);
        this.categoryEnum = categoryEnum;

    }

    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return this.getAllSupportedProjectiles();
    }

    public abstract Predicate<ItemStack> getAllSupportedProjectiles();

    public static ItemStack getHeldProjectile(LivingEntity livingEntity, Predicate<ItemStack> predicate) {
        if (predicate.test(livingEntity.getItemInHand(InteractionHand.OFF_HAND))) {
            return livingEntity.getItemInHand(InteractionHand.OFF_HAND);
        } else {
            return predicate.test(livingEntity.getItemInHand(InteractionHand.MAIN_HAND)) ? livingEntity.getItemInHand(InteractionHand.MAIN_HAND) : ItemStack.EMPTY;
        }
    }

    public int getEnchantmentValue() {
        return 1;
    }

    public abstract int getDefaultProjectileRange();

}
