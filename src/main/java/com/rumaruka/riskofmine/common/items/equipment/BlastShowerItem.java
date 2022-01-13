package com.rumaruka.riskofmine.common.items.equipment;

import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.common.config.ROMConfig;
import com.rumaruka.riskofmine.init.ROMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlastShowerItem extends EquipmentItemBase {
    private MobEffectCategory category;

    public BlastShowerItem() {
        super(CategoryEnum.UTILITY);
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
            tooltip.add(new TextComponent("ror.blast_shower.info"));
        }
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);

        if (!worldIn.isClientSide) {
            playerIn.curePotionEffects(stack);

            if (!playerIn.getAbilities().instabuild) {

                for (int i = 0; i < playerIn.getInventory().getContainerSize(); i++) {
                    ItemStack itemStack = playerIn.getInventory().getItem(i);
                    if (itemStack.getItem() == ROMItems.ALIEN_HEAD) {
                        playerIn.removeAllEffects();
                        removeNegativeEffect(playerIn);
                        playerIn.getCooldowns().addCooldown(this, ROMConfig.General.cooldownEq.get() - ROMItems.ALIEN_HEAD.cooldownMinus);
                        MinecraftForge.EVENT_BUS.register(new ProjectileRemoveEvent());
                    }

                }
                playerIn.removeAllEffects();
                removeNegativeEffect(playerIn);
                MinecraftForge.EVENT_BUS.register(new ProjectileRemoveEvent());
                playerIn.getCooldowns().addCooldown(this, ROMConfig.General.cooldownEq.get());
            }
        }

        MinecraftForge.EVENT_BUS.unregister(new ProjectileRemoveEvent());
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
    }


    private void removeNegativeEffect(LivingEntity entity) {
        List<MobEffect> potions = new ArrayList<>();
        potions.addAll(entity.getActiveEffectsMap().keySet());
        potions.stream().filter(potion -> isBadEffect()).forEach(entity::removeEffect);
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @javax.annotation.Nullable net.minecraft.nbt.CompoundTag nbt) {
        return new net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper(stack);
    }

    public boolean isBadEffect() {
        return this.category == MobEffectCategory.HARMFUL;
    }

    @Mod.EventBusSubscriber(modid = RiskOfMine.MODID)
    public static class ProjectileRemoveEvent {
        @SubscribeEvent
        public static void onManipulationProjectiles(ProjectileImpactEvent event) {
            Player playerEntity = Minecraft.getInstance().player;
            Projectile projectileEntity = (Projectile) event.getEntity();
            assert playerEntity != null;
            if (playerEntity.level.isClientSide) {
                projectileEntity.discard();
            }
        }
    }

}
