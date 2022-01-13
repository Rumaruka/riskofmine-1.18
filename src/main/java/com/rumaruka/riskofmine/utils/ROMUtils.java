package com.rumaruka.riskofmine.utils;

import com.google.common.collect.Lists;
import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.UUID;


public class ROMUtils {
    public static Minecraft minecraft = Minecraft.getInstance();

    private final List<CategoryEnum> categoryEnum = Lists.newArrayList();

    public static int durOld;

    public static ItemStack whereMyBestFriend(Player player) {

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack.getItem() == ROMItems.DIO_BEST_FRIEND) {
                return itemStack;
            }
        }

        return new ItemStack(ROMItems.DIO_BEST_FRIEND);
    }

    public static ItemStack whereMyBestFriendInCurio(Player player) {


        if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.DIO_BEST_FRIEND, player).isPresent()) {
            ItemStack curiosStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.DIO_BEST_FRIEND, player).get().right;
            if (curiosStack.getItem() == ROMItems.DIO_BEST_FRIEND) {
                return curiosStack;
            }
        }

        return new ItemStack(ROMItems.DIO_BEST_FRIEND);
    }

    public static ItemStack getStack(Item item) {
        return new ItemStack(item);
    }

    public boolean hasCategory(CategoryEnum categoryEnum) {
        return this.categoryEnum.contains(categoryEnum);
    }


    public static int getDurOld() {
        return durOld;
    }

    /**
     * set the movespeed used for the new AI system
     */
    public static int setDurOld(int durNew) {
        return durOld = durNew;
    }


    public static void sendMessage(String msg) {
        Player player = minecraft.player;

        if (player != null) {
            player.sendMessage(new TextComponent(msg), UUID.randomUUID());

        }

    }

    public boolean checkItemInInventory(ServerPlayer player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(0);
            if (itemStack.getItem() == item) {
                return true;
            }
        }

        return false;
    }

    public boolean checkItemInCurios(ServerPlayer player, Item item) {
        if (CuriosApi.getCuriosHelper().findEquippedCurio(item, player).isPresent()) {
            ItemStack curiosStack = CuriosApi.getCuriosHelper().findEquippedCurio(item, player).get().right;
            return curiosStack.getItem() == item;
        }
        return false;
    }


}
