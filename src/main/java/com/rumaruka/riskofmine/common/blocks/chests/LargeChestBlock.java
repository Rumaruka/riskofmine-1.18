package com.rumaruka.riskofmine.common.blocks.chests;

import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.blocks.chests.base.GenericChestBlock;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;
import com.rumaruka.riskofmine.common.config.ROMConfig;
import com.rumaruka.riskofmine.common.tiles.BaseChestTE;
import com.rumaruka.riskofmine.common.tiles.LargeChestTE;
import com.rumaruka.riskofmine.init.ROMSounds;
import com.rumaruka.riskofmine.init.ROMTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundSource;
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

public class LargeChestBlock extends GenericChestBlock {
    public LargeChestBlock() {
        super(ChestsTypes.LARGE, ROMTiles.LARGE_CHEST, BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, 3600000.0F));
    }


    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ROMMoney romMoney = ROMMoney.from(player);
        Money money = romMoney.money;
        if (worldIn.isClientSide) {

            return InteractionResult.SUCCESS;

        } else {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof BaseChestTE && !player.getAbilities().instabuild) {
                if (money.getCurrentMoney() < ROMConfig.General.priceLargeChest.get()) {
                    worldIn.playSound(null, pos, ROMSounds.ROM_CHEST_NOT_MONEY.get(), SoundSource.BLOCKS, 2.0F, 1.0F);
                    player.displayClientMessage(new TextComponent("riskofmine.not_money"), true);
                }
            }

            if (money.getCurrentMoney() >= ROMConfig.General.priceLargeChest.get()) {
                money.consumeMoney(player, ROMConfig.General.priceLargeChest.get());
                romMoney.detectAndSendChanges();
                player.openMenu((BaseChestTE) tileentity);
                player.awardStat(Stats.OPEN_CHEST);
                PiglinAi.angerNearbyPiglins(player, true);
            }

        }

        return InteractionResult.CONSUME;

    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new LargeChestTE(p_153215_,p_153216_);
    }
}
