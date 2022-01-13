package com.rumaruka.riskofmine.common.inventory;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.slots.SingleSlot;
import com.rumaruka.riskofmine.init.ROMContainerTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class ChestInventory extends AbstractContainerMenu {

    private final Container inventory;

    private final ChestsTypes chestType;


    public static @NotNull ChestInventory createLunarContainer(int windowId, Inventory playerInventory) {
        return new ChestInventory(ROMContainerTypes.LUNAR_CHEST, windowId, playerInventory, new SimpleContainer(ChestsTypes.LUNAR.size), ChestsTypes.LUNAR);
    }

    public static ChestInventory createLunarContainer(int windowId, Inventory playerInventory, Container inventory) {
        return new ChestInventory(ROMContainerTypes.LUNAR_CHEST, windowId, playerInventory, inventory, ChestsTypes.LUNAR);
    }


    public static @NotNull ChestInventory createLegendaryContainer(int windowId, Inventory playerInventory) {
        return new ChestInventory(ROMContainerTypes.LEGENDARY_CHEST, windowId, playerInventory, new SimpleContainer(ChestsTypes.LEGENDARY.size), ChestsTypes.LEGENDARY);
    }

    public static ChestInventory createLegendaryContainer(int windowId, Inventory playerInventory, Container inventory) {
        return new ChestInventory(ROMContainerTypes.LEGENDARY_CHEST, windowId, playerInventory, inventory, ChestsTypes.LEGENDARY);
    }

    public static @NotNull ChestInventory createLargeContainer(int windowId, Inventory playerInventory) {
        return new ChestInventory(ROMContainerTypes.LARGE_CHEST, windowId, playerInventory, new SimpleContainer(ChestsTypes.LARGE.size), ChestsTypes.LARGE);
    }

    public static ChestInventory createLargeContainer(int windowId, Inventory playerInventory, Container inventory) {
        return new ChestInventory(ROMContainerTypes.LARGE_CHEST, windowId, playerInventory, inventory, ChestsTypes.LARGE);
    }

    public static @NotNull ChestInventory createCommonContainer(int windowId, Inventory playerInventory) {
        return new ChestInventory(ROMContainerTypes.SMALL_CHEST, windowId, playerInventory, new SimpleContainer(ChestsTypes.SMALL.size), ChestsTypes.SMALL);
    }

    public static ChestInventory createCommonContainer(int windowId, Inventory playerInventory, Container inventory) {
        return new ChestInventory(ROMContainerTypes.SMALL_CHEST, windowId, playerInventory, inventory, ChestsTypes.SMALL);
    }

    public ChestInventory(MenuType<?> containerType, int windowId, Inventory playerInventory, Container inventory, ChestsTypes chestType) {
        super(containerType, windowId);
        checkContainerSize(inventory, chestType.size);

        this.inventory = inventory;
        this.chestType = chestType;

        inventory.startOpen(playerInventory.player);


        if (chestType == ChestsTypes.SMALL || chestType == ChestsTypes.LARGE || chestType == ChestsTypes.DAMAGE || chestType == ChestsTypes.HEALING || chestType == ChestsTypes.EQUIPMENT_BARREL || chestType == ChestsTypes.LUNAR || chestType == ChestsTypes.RUSTY || chestType == ChestsTypes.UTILITY) {
            this.addSlot(new SingleSlot(inventory, 0, 12 + 4 * 18, 8 + 2 * 18));
        } else {
            for (int chestRow = 0; chestRow < chestType.getRowCount(); chestRow++) {
                for (int chestCol = 0; chestCol < chestType.rowLength; chestCol++) {
                    this.addSlot(new Slot(inventory, chestCol + chestRow * chestType.rowLength, 12 + chestCol * 18, 18 + chestRow * 18));
                }
            }
        }

        int leftCol = (chestType.xSize - 162) / 2 + 1;

        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                this.addSlot(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, chestType.ySize - (4 - playerInvRow) * 18 - 10));
            }

        }

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            this.addSlot(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, chestType.ySize - 24));
        }
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return this.inventory.stillValid(playerIn);
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.chestType.size) {
                if (!this.moveItemStackTo(itemstack1, this.chestType.size, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.chestType.size, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;


    }

    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        this.inventory.stopOpen(playerIn);
    }

    @OnlyIn(Dist.CLIENT)
    public ChestsTypes getChestType() {
        return this.chestType;
    }

    public Container getInventory() {
        return this.inventory;
    }
}