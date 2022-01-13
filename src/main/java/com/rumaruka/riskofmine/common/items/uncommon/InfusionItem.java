package com.rumaruka.riskofmine.common.items.uncommon;

import com.rumaruka.riskofmine.api.CategoryEnum;
import com.rumaruka.riskofmine.api.EnumType;
import com.rumaruka.riskofmine.common.items.ItemCollectiblesBase;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class InfusionItem extends ItemCollectiblesBase {
    private static final UUID uuid1 = UUID.randomUUID();

    public InfusionItem() {
        super(EnumType.UNCOMMON, CategoryEnum.UTILITY, 16);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_) {
        tooltip.add(new TextComponent("ror.alt.info"));
        if (Screen.hasAltDown()) {

            tooltip.add(new TextComponent("riskofmine.rarity" + ":"));
            tooltip.add(new TextComponent((getColor() + getTypeName())));
            tooltip.add(new TextComponent("riskofmine.category" + ":"));
            tooltip.add(new TextComponent((getColors() + getCategoryName())));
        }
        tooltip.add(new TextComponent("ror.shiftpress.info"));
        if (Screen.hasShiftDown()) {
            tooltip.add(new TextComponent("ror.infusion.info"));
            tooltip.add(new TextComponent("[Stacks:" + stack.getCount() + "]"));
        }
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getMainHandItem();
        if (!worldIn.isClientSide()) {
            playerIn.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(uuid1, "healthBoost", stack.getCount(), AttributeModifier.Operation.ADDITION));
            if (!playerIn.isCreative() || !playerIn.getAbilities().invulnerable) {
                stack.shrink(stack.getCount());
            }
        }

        return super.use(worldIn, playerIn, handIn);
    }
}
