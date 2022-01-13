//package com.rumaruka.riskofmine.common.entity.bullets;
//
//import com.rumaruka.riskofmine.init.ROMEntitys;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.projectile.AbstractArrowEntity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.world.World;
//
//public class EntityGoldenIngotBullets extends AbstractArrowEntity {
//
//
//    public EntityGoldenIngotBullets(EntityType<? extends AbstractArrowEntity> p_i48547_1_, World p_i48546_2_) {
//        super(p_i48547_1_, p_i48546_2_);
//    }
//
//    public EntityGoldenIngotBullets(double p_i48547_2_, double p_i48547_4_, double p_i48547_6_, World p_i48547_8_) {
//        super(ROMEntitys.GOLD_BULLETS, p_i48547_2_, p_i48547_4_, p_i48547_6_, p_i48547_8_);
//    }
//
//    public EntityGoldenIngotBullets(LivingEntity p_i48548_2_, World p_i48548_3_) {
//        super(ROMEntitys.GOLD_BULLETS, p_i48548_2_, p_i48548_3_);
//    }
//    public EntityGoldenIngotBullets(World p_i46758_1_, LivingEntity p_i46758_2_) {
//        super(ROMEntitys.GOLD_BULLETS, p_i46758_2_, p_i46758_1_);
//    }
//
//
//    @Override
//    protected ItemStack getPickupItem() {
//        return new ItemStack(Items.GOLD_INGOT);
//    }
//}
