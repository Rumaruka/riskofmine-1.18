package com.rumaruka.riskofmine.common.event;

import com.rumaruka.riskofmine.common.config.ROMConfig;
import com.rumaruka.riskofmine.common.entity.HealthOrbEntity;
import com.rumaruka.riskofmine.init.ROMEffects;
import com.rumaruka.riskofmine.init.ROMItems;
import com.rumaruka.riskofmine.init.ROMParticles;
import com.rumaruka.riskofmine.utils.ROMMathFormula;
import com.rumaruka.riskofmine.utils.ROMUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.UUID;

@Mod.EventBusSubscriber
public class ItemEvent {
    private static final UUID SPEED_MODIFIER_SPRINTING_UUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");

    /**
     * onEntityDeath worked code !!
     */
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        /**
         Player kill Entity
         */
        if (event.getSource().getEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getSource().getEntity();
            LivingEntity livingEntity = event.getEntityLiving();
            Level world = livingEntity.level;

            if (!world.isClientSide) {

                if (player != null) {

                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.MONSTER_TOOTH, player).isPresent()) {
                        ItemStack curiosStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.MONSTER_TOOTH, player).get().right;
                        world.addFreshEntity(new HealthOrbEntity(world, livingEntity.getX() + 0.5d, livingEntity.getY() + 0.5d, livingEntity.getZ() + 0.5d, curiosStack.getCount() / 2));

                    }
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (itemStack.getItem() == ROMItems.MONSTER_TOOTH) {
                            world.addFreshEntity(new HealthOrbEntity(world, livingEntity.getX() + 0.5d, livingEntity.getY() + 0.5d, livingEntity.getZ() + 0.5d, itemStack.getCount() / 2));
                        }
                    }
                }

            }
        }
        /**
         Entity kill Player
         */
        if (event.getSource().getEntity() instanceof Mob && event.getEntityLiving() instanceof ServerPlayer) {
            Mob livingEntity = (Mob) event.getSource().getEntity();
            ServerPlayer player = (ServerPlayer) event.getEntityLiving();
            Level world = player.level;
            if (!world.isClientSide) {
                if (event.getSource().isBypassInvul()) {
                    return;
                } else {
                    if (livingEntity != null) {
                        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                            ItemStack itemStack = player.getInventory().getItem(i);
                            if (itemStack.getItem() == ROMItems.DIO_BEST_FRIEND) {
                                if (player.isDeadOrDying() || player.getHealth() < 2.5f) {
                                    Minecraft.getInstance().gameRenderer.displayItemActivation(ROMUtils.whereMyBestFriend(player));
                                    player.setHealth(player.getMaxHealth());
                                    player.removeAllEffects();
                                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1800, 2));
                                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 2));
                                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1600, 1));
                                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 1));
                                    player.level.broadcastEntityEvent(player, (byte) 35);


                                    itemStack.shrink(1);


                                }
                            }


                        }
                        if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.DIO_BEST_FRIEND, player).isPresent()) {
                            ItemStack curiosStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.DIO_BEST_FRIEND, player).get().right;
                            if (curiosStack.getItem() == ROMItems.DIO_BEST_FRIEND) {
                                if (player.isDeadOrDying() || player.getHealth() < 2.5f) {

                                    player.setHealth(player.getMaxHealth());
                                    player.removeAllEffects();
                                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1800, 2));
                                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200, 2));
                                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1600, 1));
                                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 1));
                                    player.level.broadcastEntityEvent(player, (byte) 35);

                                    Minecraft.getInstance().gameRenderer.displayItemActivation(ROMUtils.whereMyBestFriendInCurio(player));
                                    curiosStack.shrink(1);
                                }

                            }
                        }


                    }
                }


            }
        }
    }

    /**
     * onEntityHurt worked code !!
     * priority = EventPriority.LOW
     * If item use hurt and damage add only this method
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onEntityHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getSource().getEntity();
            LivingEntity livingEntity = event.getEntityLiving();
            Level world = livingEntity.level;
            if (!world.isClientSide) {
                if (player != null) {
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (itemStack.getItem() == ROMItems.GASOLINE && event.getEntity() instanceof Mob) {
                            event.getEntity().setRemainingFireTicks(itemStack.getCount() * 20);
                        }
                    }


                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.GASOLINE, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.GASOLINE, player).get().right;
                        if (event.getEntity() instanceof Mob) {
                            event.getEntity().setRemainingFireTicks(curioStack.getCount() * 20);
                        }
                    }
                }
                if (player != null) {
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (itemStack.getItem() == ROMItems.CROWBAR && event.getEntity() instanceof Mob) {
                            if (((Mob) event.getEntity()).getHealth() > (((Mob) event.getEntity()).getMaxHealth() * 90 / 100)) {

                                event.getEntity().hurt(DamageSource.ANVIL, (float) (itemStack.getCount() * 1.00115d));
                            }
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.CROWBAR, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.CROWBAR, player).get().right;
                        if (curioStack.getItem() == ROMItems.CROWBAR && event.getEntity() instanceof Mob) {
                            if (((Mob) event.getEntity()).getHealth() > (((Mob) event.getEntity()).getMaxHealth() * 90 / 100)) {

                                event.getEntity().hurt(DamageSource.ANVIL, (float) (curioStack.getCount() * 1.00115d));
                            }
                        }
                    }
                }
                if (player != null) {
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (itemStack.getItem() == ROMItems.ARMOR_PIERCING_ROUNDS && (event.getEntity() instanceof WitherBoss || event.getEntity() instanceof EnderDragon || !event.getEntity().canChangeDimensions())) {
                            event.getEntity().hurt(DamageSource.ANVIL, itemStack.getCount() * 2 - 1);
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.ARMOR_PIERCING_ROUNDS, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.ARMOR_PIERCING_ROUNDS, player).get().right;
                        if (curioStack.getItem() == ROMItems.ARMOR_PIERCING_ROUNDS && event.getEntity() instanceof Mob) {
                            if (curioStack.getItem() == ROMItems.ARMOR_PIERCING_ROUNDS && (event.getEntity() instanceof WitherBoss || event.getEntity() instanceof EnderDragon || !event.getEntity().canChangeDimensions())) {
                                event.getEntity().hurt(DamageSource.ANVIL, curioStack.getCount() * 2 - 1);
                            }
                        }
                    }
                }
                if (player != null) {
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (itemStack.getItem() == ROMItems.CHRONOBAUBLE && event.getEntity() instanceof Mob) {
                            ((Mob) event.getEntity()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, itemStack.getCount() * 2, 4));
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.CHRONOBAUBLE, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.CHRONOBAUBLE, player).get().right;
                        if (curioStack.getItem() == ROMItems.CHRONOBAUBLE && event.getEntity() instanceof Mob) {
                            if (curioStack.getItem() == ROMItems.CHRONOBAUBLE && event.getEntity() instanceof Mob) {
                                ((Mob) event.getEntity()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, curioStack.getCount() * 2, 4));
                            }
                        }
                    }
                }
                if (player != null) {
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (itemStack.getItem() == ROMItems.TRI_TIP_DAGGER && event.getEntity() instanceof Mob) {
                            ((Mob) event.getEntity()).addEffect(new MobEffectInstance(ROMEffects.BLEED.get(), ROMConfig.General.durBleedConfig.get() * itemStack.getCount(), 2, true, false));
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.TRI_TIP_DAGGER, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.TRI_TIP_DAGGER, player).get().right;
                        if (curioStack.getItem() == ROMItems.TRI_TIP_DAGGER && event.getEntity() instanceof Mob) {
                            if (curioStack.getItem() == ROMItems.TRI_TIP_DAGGER && event.getEntity() instanceof Mob) {
                                ((Mob) event.getEntity()).addEffect(new MobEffectInstance(ROMEffects.BLEED.get(), ROMConfig.General.durBleedConfig.get() * curioStack.getCount(), 2, true, false));
                            }
                        }
                    }
                    if (event.getEntity() instanceof Mob) {
                        if (((Mob) event.getEntity()).hasEffect(ROMEffects.BLEED.get())) {
                            event.getEntity().hurt(DamageSource.ANVIL, 4.0f);
                        }
                    }
                }
                if (player != null) {
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (itemStack.getItem() == ROMItems.STUN_GRENADE && event.getEntity() instanceof Mob) {
                            ((Mob) event.getEntity()).addEffect(new MobEffectInstance(ROMEffects.STUN.get(), ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get() * itemStack.getCount()), 2, true, false));
                        }
                    }
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.STUN_GRENADE, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.STUN_GRENADE, player).get().right;
                        if (curioStack.getItem() == ROMItems.STUN_GRENADE && event.getEntity() instanceof Mob) {
                            if (curioStack.getItem() == ROMItems.STUN_GRENADE && event.getEntity() instanceof Mob) {
                                ((Mob) event.getEntity()).addEffect(new MobEffectInstance(ROMEffects.STUN.get(), ROMUtils.setDurOld(ROMConfig.General.durStunConfig.get() * curioStack.getCount()), 2, true, false));
                            }
                        }
                    }

                }
                if (player != null) {

                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (itemStack.getItem() == ROMItems.STICKY_BOMB && event.getEntity() instanceof Mob) {
                            //TODO: Make mini bomb entity how magnite in entity
//                            event.getEntity().level.explode(event.getEntity(), event.getEntity().getX(), event.getEntity().getY(0.0625D), event.getEntity().getZ(), 4.0F, Explosion.Mode.BREAK);
//                            player.setHealth(5);


                        }
                    }
                }
                if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.STICKY_BOMB, player).isPresent()) {
                    ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.STUN_GRENADE, player).get().right;
                    if (curioStack.getItem() == ROMItems.STICKY_BOMB && event.getEntity() instanceof Mob) {
                        if (curioStack.getItem() == ROMItems.STICKY_BOMB && event.getEntity() instanceof Mob) {

//                            event.getEntity().level.explode(event.getEntity(), event.getEntity().getX(), event.getEntity().getY(0.0625D), event.getEntity().getZ(), 4.0F, Explosion.Mode.BREAK);

//                                        player.setHealth(1.5f);


                        }
                    }
                }

            }
        }
    }


    /**
     * onEntityUpdate worked code !!
     * If item use updating entity
     */
    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        Player player = Minecraft.getInstance().player;

        LivingEntity livingEntity = event.getEntityLiving();

        Level world = livingEntity.level;
        if (!world.isClientSide) {
            if (player != null) {
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack.getItem() == ROMItems.FOCUS_CRYSTAL) {
                        if (livingEntity instanceof Mob) {
                            float distance = player.distanceTo(livingEntity);

                            if (distance <= 3.5) {
                                livingEntity.hurt(DamageSource.ANVIL, ROMMathFormula.powerIncreasing(itemStack.getCount(), 5.0f));
                                Minecraft.getInstance().particleEngine.createTrackingEmitter(livingEntity, ROMParticles.FOCUS_CRYSTAL.get());
                            }


                        }
                    }

                    if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.FOCUS_CRYSTAL, player).isPresent()) {
                        ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.FOCUS_CRYSTAL, player).get().right;
                        if (curioStack.getItem() == ROMItems.FOCUS_CRYSTAL) {

                            float distance = player.distanceTo(livingEntity);
                            if (distance <= 3.5) {
                                livingEntity.hurt(DamageSource.ANVIL, curioStack.getCount());
                                Minecraft.getInstance().particleEngine.createTrackingEmitter(livingEntity, ROMParticles.FOCUS_CRYSTAL.get());
                            }
                        }
                    }

                }


            }


        }

    }

    /**
     * onPlayerHurt  - for hurt player event
     *
     * @param event
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onPlayerHurt(LivingHurtEvent event) {

        if (event.getSource().getEntity() instanceof Mob && event.getEntityLiving() instanceof ServerPlayer) {

            ServerPlayer player = (ServerPlayer) event.getEntityLiving();
            Level world = player.level;
            if (!world.isClientSide) {

                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack.getItem() == ROMItems.OLD_WAR_STEALTHKIT) {
                        if (player.getHealth() >= 2.5f) {
                            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 1000, 1, false, false));
                            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1000, 1, false, false));
                        }

                    }

                }
                if (CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.OLD_WAR_STEALTHKIT, player).isPresent()) {
                    ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(ROMItems.OLD_WAR_STEALTHKIT, player).get().right;
                    if (curioStack.getItem() == ROMItems.OLD_WAR_STEALTHKIT) {
                        if (curioStack.getItem() == ROMItems.OLD_WAR_STEALTHKIT) {
                            if (player.getHealth() >= 2.5f) {
                                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 1000, 1, false, false));
                                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1000, 1, false, false));
                            }
                        }
                    }
                }

            }


        }
    }

    /*
        @SubscribeEvent
        public void onXpLevelUp(PlayerXpEvent.LevelChange event) {

            PlayerEntity player = event.getPlayer();
            int levels = event.getLevels();
            player.experienceLevel += levels;
            World level = player.level;
            BlockPos pos = player.blockPosition();
            if (levels > 0 && player.experienceLevel % 2 == 0) {


                player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ROMSounds.ROM_PLAYER_LEVEL_UP.get(), SoundCategory.MASTER, 1F, 1.0F);
                for (int i = 0; i < player.inventory.getContainerSize(); i++) {
                    ItemStack itemStack = player.inventory.getItem(i);
                    if (itemStack.getItem() == ROMItems.WARBANNER) {
                        BlockState state = level.getBlockState(pos);
                        Block block = state.getBlock();
                        if (block == ROMBlocks.WAR_BANNER_BLOCK) {
                            level.setBlock(pos, state, 4);
                        }
                    }
                }
            }

        }
    */
//TODO: EntityMissiles and Fireworks
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void attacksDronesFireworksMissiles(LivingAttackEvent event) {

    }


}








