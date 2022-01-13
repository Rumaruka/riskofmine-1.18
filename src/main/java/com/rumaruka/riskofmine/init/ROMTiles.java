package com.rumaruka.riskofmine.init;

import com.rumaruka.riskofmine.client.tesr.CommonChestTESR;
import com.rumaruka.riskofmine.client.tesr.MultiShopTESR;
import com.rumaruka.riskofmine.common.tiles.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ObjectHolder;
import ru.timeconqueror.timecore.api.registry.TileEntityRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import static com.rumaruka.riskofmine.RiskOfMine.MODID;
import static ru.timeconqueror.timecore.api.util.Hacks.promise;

@ObjectHolder(MODID)
public class ROMTiles {


    //World

    //Chest
    public static final BlockEntityType<CommonChestTE> SMALL_CHEST = promise();
    public static final BlockEntityType<LargeChestTE> LARGE_CHEST = promise();
    public static final BlockEntityType<LegendaryChestTE> LEGENDARY_CHEST = promise();
    public static final BlockEntityType<LunarChestTE> LUNAR_CHEST = promise();
    //Shop
    public static final BlockEntityType<MultiShopTE> MULTI_SHOP = promise();
    public static final BlockEntityType<EquipmentTripleBarrelTE> EQUIPMENT_TRIPLE_BARREL = promise();

    private static class Setup {

        @AutoRegistrable
        private static final TileEntityRegister REGISTER = new TileEntityRegister(MODID);

        @AutoRegistrable.Init
        private static void register() {

            REGISTER.registerSingleBound("small_chest", CommonChestTE::new, () -> ROMBlocks.SMALL_CHEST).regCustomRenderer(() -> CommonChestTESR::new);
            REGISTER.registerSingleBound("large_chest", LargeChestTE::new, () -> ROMBlocks.LARGE_CHEST);
            REGISTER.registerSingleBound("legendary_chest", LegendaryChestTE::new, () -> ROMBlocks.LARGE_CHEST);
            REGISTER.registerSingleBound("lunar_chest", LunarChestTE::new, () -> ROMBlocks.LUNAR_CHEST);

            REGISTER.registerSingleBound("multi_shop", MultiShopTE::new, () -> ROMBlocks.MULTI_SHOP).regCustomRenderer(() -> MultiShopTESR::new);
            REGISTER.registerSingleBound("equipment_triple_barrel", EquipmentTripleBarrelTE::new, () -> ROMBlocks.EQUIPMENT_TRIPLE_BARREL);


        }
    }
}


