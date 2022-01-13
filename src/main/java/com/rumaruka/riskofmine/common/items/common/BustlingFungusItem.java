package com.rumaruka.riskofmine.common.items.common;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import com.rumaruka.riskofmine.events.MovingHandler;
import com.rumaruka.riskofmine.init.ROMItems;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class BustlingFungusItem extends ItemCollectiblesBase {
    public BustlingFungusItem() {
        super(EnumType.COMMON, CategoryEnum.HEALING, 64);
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
            tooltip.add(new TextComponent("ror.bf.info"));
            tooltip.add(new TextComponent("[Stacks:" + stack.getCount() + "]"));
        }
    }
    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isClientSide()) {
            if (!MovingHandler.isMoving((ServerPlayer) entityIn)) {

                ((ServerPlayer) entityIn).heal((stack.getCount() + 0.045f) / 20f);

            }

        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean canRightClickEquip(ItemStack stack) {
        return true;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof ServerPlayer) {
            if (!livingEntity.level.isClientSide()) {
                if (!MovingHandler.isMoving((ServerPlayer) livingEntity)) {

                    livingEntity.heal((stack.getCount() + 0.045f) / 20f);

                }
            }
        }
    }

    @Override
    public boolean canEquip(String identifier, LivingEntity livingEntity, ItemStack stack) {
        return !CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.BUSTLING_FUNGUS, livingEntity)
                .isPresent();
    }


    @Override
    public boolean canSync(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }


}
