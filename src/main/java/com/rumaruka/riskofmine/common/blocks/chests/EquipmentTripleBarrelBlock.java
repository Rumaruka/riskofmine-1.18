package com.rumaruka.riskofmine.common.blocks.chests;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.blocks.chests.base.GenericShopBlock;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.common.tiles.BaseShopTE;
import com.rumaruka.riskofmine.common.tiles.EquipmentTripleBarrelTE;
import com.rumaruka.riskofmine.init.ROMSounds;
import com.rumaruka.riskofmine.init.ROMTiles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class EquipmentTripleBarrelBlock extends GenericShopBlock {
    public EquipmentTripleBarrelBlock() {
        super(ChestsTypes.EQUIPMENT_TRIPLE_BARREL, ROMTiles.MULTI_SHOP, Properties.of(Material.STONE).strength(5.0F, 5.0F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE).setValue(CLOSED,Boolean.FALSE));

    }
    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ROMMoney romMoney = ROMMoney.from(player);
        Money money = romMoney.money;
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof BaseShopTE &&!player.getAbilities().instabuild) {
                if(money.getCurrentMoney()==0){
                    worldIn.playSound(null, pos, ROMSounds.ROM_CHEST_NOT_MONEY.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
                    player.displayClientMessage(new TextComponent("riskofmine.not_money"), true);
                }
                if(money.getCurrentMoney()>0 && state.getValue(CLOSED)==Boolean.FALSE){
                    money.consumeMoney(player,25);
                    romMoney.detectAndSendChanges();
                    player.openMenu((BaseShopTE) tileentity);
                    player.awardStat(Stats.OPEN_CHEST);
                    PiglinAi.angerNearbyPiglins(player, true);
                    BlockState blockstate = state.setValue(CLOSED, Boolean.TRUE);
                    worldIn.setBlock(pos,blockstate,3);
                }

                if(state.getValue(CLOSED)==Boolean.TRUE){
                    worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
                    return InteractionResult.FAIL;
                }
            }



        }

        return InteractionResult.CONSUME;

    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new EquipmentTripleBarrelTE(p_153215_,p_153216_);
    }



}
