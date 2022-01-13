package com.rumaruka.riskofmine.common.tiles;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.inventory.ChestShopInventory;
import com.rumaruka.riskofmine.init.ROMBlocks;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class EquipmentTripleBarrelTE extends BaseShopTE {
    public EquipmentTripleBarrelTE(BlockPos blockPos, BlockState blockState) {
        super(ROMTiles.EQUIPMENT_TRIPLE_BARREL, blockPos, blockState, ChestsTypes.EQUIPMENT_TRIPLE_BARREL, ROMBlocks.EQUIPMENT_TRIPLE_BARREL);
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory) {
        return ChestShopInventory.createEquipmentTripleBarrelContainer(windowId, playerInventory, this);
    }
}
