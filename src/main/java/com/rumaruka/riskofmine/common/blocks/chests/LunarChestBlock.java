package com.rumaruka.riskofmine.common.blocks.chests;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.blocks.chests.base.GenericChestBlock;
import com.rumaruka.riskofmine.common.cap.lunar.ROMLunar;
import com.rumaruka.riskofmine.common.cap.lunar.data.Lunar;

import com.rumaruka.riskofmine.common.tiles.BaseChestTE;
import com.rumaruka.riskofmine.common.tiles.LunarChestTE;
import com.rumaruka.riskofmine.init.ROMTiles;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class LunarChestBlock extends GenericChestBlock {
    public LunarChestBlock() {
        super(ChestsTypes.LUNAR, ROMTiles.LUNAR_CHEST, BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ROMLunar romLunar = ROMLunar.from(player);
        Lunar lunar = romLunar.lunar;
        if (worldIn.isClientSide) {

            return InteractionResult.SUCCESS;

        } else {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);

            if (tileentity instanceof BaseChestTE &&!player.getAbilities().instabuild) {
                if(lunar.getCurrentLunar()>0){
                    lunar.consumeLunar(player,1.0f);
                    romLunar.detectAndSendChanges();
                    player.openMenu((BaseChestTE) tileentity);
                    player.awardStat(Stats.OPEN_CHEST);
                    PiglinAi.angerNearbyPiglins(player, true);
                }


            }
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new LunarChestTE(p_153215_,p_153216_);
    }


}
