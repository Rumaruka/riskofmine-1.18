package com.rumaruka.riskofmine.common.tiles;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.api.ChestsTypes;
import com.rumaruka.riskofmine.common.blocks.chests.*;
import com.rumaruka.riskofmine.common.blocks.chests.base.GenericShopBlock;
import com.rumaruka.riskofmine.common.inventory.ChestShopInventory;
import com.rumaruka.riskofmine.init.ROMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class BaseShopTE extends RandomizableContainerBlockEntity implements LidBlockEntity {


    private NonNullList<ItemStack> chestContents;
    protected float lidAngle;
    protected float prevLidAngle;
    protected int numPlayersUsing;
    private int ticksSinceSync;
    private final ChestsTypes chestType;
    private final Block blockToUse;

    protected BaseShopTE(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, ChestsTypes chestTypeIn, Block blockToUseIn) {
        super(blockEntityType, blockPos, blockState);

        this.chestContents = NonNullList.<ItemStack>withSize(chestTypeIn.size, ItemStack.EMPTY);
        this.chestType = chestTypeIn;
        this.blockToUse = blockToUseIn;
    }

    @Override
    public int getContainerSize() {
        return this.chestType.size;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.chestContents) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected Component getDefaultName() {
        return new TextComponent(RiskOfMine.MODID + ".container." + this.chestType.getId() + "_chest");
    }

    public void load(BlockState state, CompoundTag compound) {
        super.load(compound);

        this.loadFromTag(compound);


    }

    public void loadFromTag(CompoundTag p_190586_1_) {
        this.chestContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(p_190586_1_) && p_190586_1_.contains("Items", 9)) {
            ContainerHelper.loadAllItems(p_190586_1_, this.chestContents);
        }

    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);

        return saveToTag(compound);
    }

    public CompoundTag saveToTag(CompoundTag p_190580_1_) {
        if (!this.trySaveLootTable(p_190580_1_)) {
            ContainerHelper.saveAllItems(p_190580_1_, this.chestContents, false);
        }

        return p_190580_1_;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return save(new CompoundTag()
        );
    }


    public void handleUpdateTag(BlockState state, CompoundTag tag) {
        load(tag);
    }


    public void serverTick() {
        int i = this.worldPosition.getX();
        int j = this.worldPosition.getY();
        int k = this.worldPosition.getZ();
        ++this.ticksSinceSync;
        assert this.level != null;
        this.numPlayersUsing = getNumberOfPlayersUsing(this.level, this, this.ticksSinceSync, i, j, k, this.numPlayersUsing);
        this.prevLidAngle = this.lidAngle;
        float f = 0.1F;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {

            this.playSound(ROMSounds.ROM_CHEST_OPEN.get());
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f1 = this.lidAngle;
            if (this.numPlayersUsing > 0) {
                this.lidAngle += 0.1F;
            } else {
                this.lidAngle -= 0.1F;
            }

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }

            float f2 = 0.5F;
            if (this.lidAngle < 0.5F && f1 >= 0.5F) {
                this.playSound(ROMSounds.ROM_CHEST_OPEN.get());
            }

            if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }
        }
    }

    public static int getNumberOfPlayersUsing(Level worldIn, RandomizableContainerBlockEntity lockableTileEntity, int ticksSinceSync, int x, int y, int z, int numPlayersUsing) {
        if (!worldIn.isClientSide && numPlayersUsing != 0 && (ticksSinceSync + x + y + z) % 200 == 0) {
            numPlayersUsing = getNumberOfPlayersUsing(worldIn, lockableTileEntity, x, y, z);
        }

        return numPlayersUsing;
    }

    public static int getNumberOfPlayersUsing(Level world, RandomizableContainerBlockEntity lockableTileEntity, int x, int y, int z) {
        int i = 0;

        for (Player playerentity : world.getEntitiesOfClass(Player.class, new AABB((float) x - 5.0F, (float) y - 5.0F, (float) z - 5.0F, (float) (x + 1) + 5.0F, (float) (y + 1) + 5.0F, (float) (z + 1) + 5.0F))) {
            if (playerentity.containerMenu instanceof ChestShopInventory) {
                ++i;
            }
        }

        return i;
    }

    private void playSound(SoundEvent soundIn) {
        double d0 = (double) this.worldPosition.getX() + 0.5D;
        double d1 = (double) this.worldPosition.getY() + 0.5D;
        double d2 = (double) this.worldPosition.getZ() + 0.5D;

        assert this.level != null;
        this.level.playSound(null, d0, d1, d2, soundIn, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }


    @Override
    public void startOpen(Player player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }

            ++this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    protected void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();

        if (block instanceof GenericShopBlock) {
            assert this.level != null;
            this.level.blockEvent(this.worldPosition, block, 1, this.numPlayersUsing);
            this.level.updateNeighborsAt(this.worldPosition, block);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.chestContents = NonNullList.withSize(this.getChestType().size, ItemStack.EMPTY);

        for (int i = 0; i < itemsIn.size(); i++) {
            if (i < this.chestContents.size()) {
                this.getItems().set(i, itemsIn.get(i));
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getOpenNess(float partialTicks) {
        return Mth.lerp(partialTicks, this.prevLidAngle, this.lidAngle);
    }

    public static int getOpenCount(BlockGetter reader, BlockPos posIn) {
        BlockState blockstate = reader.getBlockState(posIn);
        if (blockstate.hasBlockEntity()) {
            BlockEntity tileentity = reader.getBlockEntity(posIn);
            if (tileentity instanceof BaseShopTE) {
                return ((BaseShopTE) tileentity).numPlayersUsing;
            }
        }

        return 0;
    }

    @Override
    protected AbstractContainerMenu createMenu(int windowId, Inventory playerInventory) {
        return ChestMenu.sixRows(windowId, playerInventory, this);
    }

    public void wasPlaced(LivingEntity livingEntity, ItemStack stack) {
    }

    public void removeAdornments() {
    }

    public ChestsTypes getChestType() {
        ChestsTypes type = ChestsTypes.SMALL;

        if (this.hasLevel()) {
            ChestsTypes typeFromBlock = GenericShopBlock.getTypeFromBlock(this.getBlockState().getBlock());

            if (typeFromBlock != null) {
                type = typeFromBlock;
            }
        }

        return type;
    }

    public Block getBlockToUse() {
        return this.blockToUse;
    }
}