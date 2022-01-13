package com.rumaruka.riskofmine.common.items.equipment;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.common.cap.money.ROMMoney;
import com.rumaruka.riskofmine.common.cap.money.data.Money;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class TheCrowdFunderItem extends EquipmentShootItemBase {
    public TheCrowdFunderItem() {
        super(CategoryEnum.DAMAGE);
    }


    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_) {
        tooltip.add(new TextComponent("ror.alt.info"));
        if (Screen.hasAltDown()) {

            tooltip.add(new TextComponent("riskofmine.rarity" + ":"));
            tooltip.add(new TextComponent((getColor() + getTypeName())));
            tooltip.add(new TextComponent("riskofmine.category" + ":"));
            tooltip.add(new TextComponent((getColors() + getCategoryName())));
        }
        tooltip.add(new TextComponent("ror.shiftpress.info"));
        if (Screen.hasShiftDown()) {
            tooltip.add(new TextComponent("ror.the_crowdfunder.info"));
        }
    }


    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int time) {
        if (livingEntity instanceof Player) {
            Player playerentity = (Player) livingEntity;
            int i = this.getUseDuration(stack) - time;

            float f = getPowerForCharge(i);
            ROMMoney romMoney = ROMMoney.from(playerentity);
            Money money = romMoney.money;
            if (!level.isClientSide) {
//                EntityGoldenIngotBullets bullets = money.createBullets(level, money, playerentity);
//                bullets = bullets(bullets);
//                bullets.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * 3.0F, 1.0F);
                if (!playerentity.getAbilities().instabuild && money.getCurrentMoney() > 0) {
                    money.consumeMoney(playerentity, 0.5f);
                }
            }

        }
    }


    public static float getPowerForCharge(int charge) {
        float f = (float) charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean flag = !player.getProjectile(itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, level, player, hand, flag);
        if (ret != null) return ret;

        if (!player.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 72000;
    }


//    public EntityGoldenIngotBullets bullets(EntityGoldenIngotBullets bullets) {
//        return bullets;
//    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return GOLDEN_INGOT;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 20;
    }
}
