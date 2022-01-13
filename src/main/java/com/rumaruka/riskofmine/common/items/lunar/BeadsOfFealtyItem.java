package com.rumaruka.riskofmine.common.items.lunar;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BeadsOfFealtyItem extends ItemCollectiblesBase {
    public BeadsOfFealtyItem() {
        super(EnumType.LUNAR, CategoryEnum.UTILITY, 1);
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
            tooltip.add(new TextComponent("ror.beadsoffealty.info"));
        }

    }
}
