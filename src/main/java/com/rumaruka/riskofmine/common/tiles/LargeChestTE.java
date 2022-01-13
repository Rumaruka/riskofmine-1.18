package com.rumaruka.riskofmine.common.tiles;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.ChestInventory;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class LargeChestTE extends BaseChestTE {
    public LargeChestTE(BlockPos blockPos, BlockState blockState) {
        super(ROMTiles.LARGE_CHEST, blockPos, blockState, ChestsTypes.LARGE, ROMBlocks.LARGE_CHEST);
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory) {
        return ChestInventory.createLargeContainer(windowId, playerInventory, this);
    }

}
