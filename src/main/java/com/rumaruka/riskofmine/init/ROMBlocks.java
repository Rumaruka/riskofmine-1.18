package com.rumaruka.riskofmine.init;


import com.rumaruka.riskofmine.ModSetup;
import com.rumaruka.riskofmine.common.blocks.WarBannerBlock;
import com.rumaruka.riskofmine.common.blocks.chests.*;
import com.rumaruka.riskofmine.common.blocks.level.firststage.BlockDistantRoostGrass;
import net.minecraftforge.registries.ObjectHolder;
import ru.timeconqueror.timecore.api.registry.BlockRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;
import static ru.timeconqueror.timecore.api.util.Hacks.promise;

@ObjectHolder(MODID)
public class ROMBlocks {


    //World @todo: For new dimension ()
    // public static final DistantRoostGrassBlock DISTANT_ROOST_GRASS= promise();

    //Chest
    public static final SmallChestBlock SMALL_CHEST = promise();
    public static final LargeChestBlock LARGE_CHEST = promise();
    public static final LegendaryChestBlock LEGENDARY_CHEST = promise();
    public static final LunarChestBlock LUNAR_CHEST = promise();
//  public static final EquipmentBarrelBlock EQUIPMENT_BARREL = promise();
//  public static final DamageChestBlock DAMAGE_CHEST = promise();
//  public static final HealingChestBlock HEALING_CHEST = promise();
//  public static final UtilityChestBlock UTILITY_CHEST = promise();
//  public static final RustyChestBlock RUSTY_CHEST = promise();

    //Shop
    public static final MultiShopBlock MULTI_SHOP = promise();
    public static final EquipmentTripleBarrelBlock EQUIPMENT_TRIPLE_BARREL = promise();

    //Block in Item
    public static final WarBannerBlock WAR_BANNER_BLOCK = promise();


    //Block levels
    public static final BlockDistantRoostGrass DISTANT_ROOST_GRASS = promise();

    private static class Setup {

        @AutoRegistrable
        private static final BlockRegister REGISTER = new BlockRegister(MODID);

        @AutoRegistrable.Init
        private static void register() {


            REGISTER.register("small_chest", SmallChestBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
            REGISTER.register("large_chest", LargeChestBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
            REGISTER.register("legendary_chest", LegendaryChestBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
            REGISTER.register("lunar_chest", LunarChestBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
//
           REGISTER.register("multi_shop", MultiShopBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
          REGISTER.register("equipment_triple_barrel", EquipmentTripleBarrelBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
//
//            REGISTER.register("war_banner", WarBannerBlock::new).oneVarStateAndCubeAllModel().defaultBlockItem(ModSetup.ITEM_GROUP);
//
//
//            REGISTER.register("distant_roost_grass", BlockDistantRoostGrass::new).defaultBlockItem(ModSetup.ITEM_GROUP);
        }
    }


}