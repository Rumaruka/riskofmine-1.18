package com.rumaruka.riskofmine.common.items.gameplay;

import com.rumaruka.riskofmine.ModSetup;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.lunar.data.Lunar;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LunarCoinItem extends Item {
    public LunarCoinItem() {
        super(new Properties().tab(ModSetup.ITEM_GROUP));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ROMLunar romLunar = ROMLunar.from(player);
        Lunar lunar = romLunar.lunar;
        lunar.addLunar(player, 1);
        romLunar.detectAndSendChanges();
        itemStack.shrink(1);
        return InteractionResultHolder.success(itemStack);
    }


}
