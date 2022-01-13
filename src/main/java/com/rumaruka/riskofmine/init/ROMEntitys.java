package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.common.entity.HealthOrbEntity;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.ObjectHolder;
import ru.timeconqueror.timecore.api.registry.EntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;



@ObjectHolder(MODID)
public class ROMEntitys {
    @AutoRegistrable
    private static final EntityRegister REGISTER = new EntityRegister(MODID);


    public static final EntityType<HealthOrbEntity> HEALTH_ORB = REGISTER.register("health_orb",
            EntityType.Builder.<HealthOrbEntity>of(HealthOrbEntity::new, MobCategory.MISC)
                    .setTrackingRange(80)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(3.5F, 3.5F))
            .retrieve();

//    public static final EntityType<EntityGoldenIngotBullets> GOLD_BULLETS = REGISTER.register("gold_bullets",
//                    EntityType.Builder.<EntityGoldenIngotBullets>of(EntityGoldenIngotBullets::new, MobCategory.MISC)
//                            .setTrackingRange(80)
//                            .setShouldReceiveVelocityUpdates(true)
//                            .sized(3.5F, 3.5F))
//            .retrieve();



}
