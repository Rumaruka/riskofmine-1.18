package com.rumaruka.riskofmine.init;


import com.rumaruka.riskofmine.common.items.common.*;
import com.rumaruka.riskofmine.common.items.equipment.*;
import com.rumaruka.riskofmine.common.items.gameplay.LunarCoinItem;
import com.rumaruka.riskofmine.common.items.legendary.AlienHeadItem;
import com.rumaruka.riskofmine.common.items.legendary.DioBestFriendItem;
import com.rumaruka.riskofmine.common.items.lunar.BeadsOfFealtyItem;
import com.rumaruka.riskofmine.common.items.lunar.ShapedGlassItem;
import com.rumaruka.riskofmine.common.items.uncommon.ChronobaubleItem;
import com.rumaruka.riskofmine.common.items.uncommon.InfusionItem;
import com.rumaruka.riskofmine.common.items.uncommon.OldWarStealthkitItem;
import net.minecraftforge.registries.ObjectHolder;
import ru.timeconqueror.timecore.api.registry.ItemRegister;
import ru.timeconqueror.timecore.api.registry.util.AutoRegistrable;

import static com.rumaruka.riskofmine.RiskOfMine.*;
import static ru.timeconqueror.timecore.api.util.Hacks.promise;

@ObjectHolder(MODID)
public class ROMItems {
    public static final ArmorPiercingRoundsItem ARMOR_PIERCING_ROUNDS = promise();
    public static final BustlingFungusItem BUSTLING_FUNGUS = promise();
    public static final GasolineItem GASOLINE = promise();
    public static final InfusionItem INFUSION = promise();
    public static final ShapedGlassItem SHAPED_GLASS = promise();
    public static final SoldierSyringeItem SOLDIER_SYRINGE = promise();
    public static final MonsterToothItem MONSTER_TOOTH = promise();
    public static final CrowbarItem CROWBAR = promise();
    public static final EnergyDrinkItem ENERGY_DRINK = promise();
    public static final BeadsOfFealtyItem BEADS_OF_FEALTY = promise();
    public static final ChronobaubleItem CHRONOBAUBLE = promise();
    public static final BlastShowerItem BLAST_SHOWER = promise();
    public static final FocusCrystalItem FOCUS_CRYSTAL = promise();
    public static final DioBestFriendItem DIO_BEST_FRIEND = promise();
    public static final AlienHeadItem ALIEN_HEAD = promise();
    public static final OldWarStealthkitItem OLD_WAR_STEALTHKIT = promise();
    public static final TriTipDaggerItem TRI_TIP_DAGGER = promise();
    public static final StunGrenadeItem STUN_GRENADE = promise();
    public static final WarbannerItem WARBANNER = promise();
    public static final TheCrowdFunderItem THE_CROWDFUNDER = promise();
    public static final StickyBombItem STICKY_BOMB = promise();

    public static final LunarCoinItem LUNAR_COIN =promise();

    private static class Setup {

        @AutoRegistrable
        private static final ItemRegister REGISTER = new ItemRegister(MODID);

        @AutoRegistrable.Init
        private static void register() {
            REGISTER.register("armor_piercing_rounds", ArmorPiercingRoundsItem::new).defaultModel(tl("items/armor_piercing_rounds"));
            REGISTER.register("bustling_fungus", BustlingFungusItem::new).defaultModel(tl("items/bustling_fungus"));
            REGISTER.register("gasoline", GasolineItem::new).defaultModel(tl("items/gasoline"));
            REGISTER.register("infusion", InfusionItem::new).defaultModel(tl("items/infusion"));
            REGISTER.register("shaped_glass", ShapedGlassItem::new).defaultModel(tl("items/shaped_glass"));
            REGISTER.register("soldier_syringe", SoldierSyringeItem::new).defaultModel(tl("items/soldier_syringe"));
            REGISTER.register("monster_tooth", MonsterToothItem::new).defaultModel(tl("items/monster_tooth"));
            REGISTER.register("crowbar", CrowbarItem::new).defaultModel(tl("items/crowbar"));
            REGISTER.register("energy_drink", EnergyDrinkItem::new).defaultModel(tl("items/energy_drink"));
            REGISTER.register("beads_of_fealty", BeadsOfFealtyItem::new).defaultModel(tl("items/beads_of_fealty"));
            REGISTER.register("chronobauble", ChronobaubleItem::new).defaultModel(tl("items/chronobauble"));
            REGISTER.register("blast_shower", BlastShowerItem::new).defaultModel(tl("items/blast_shower"));
            REGISTER.register("focus_crystal", FocusCrystalItem::new).defaultModel(tl("items/focus_crystal"));
            REGISTER.register("dio_best_friend", DioBestFriendItem::new).defaultModel(tl("items/dio_best_friend"));
            REGISTER.register("alien_head", AlienHeadItem::new).defaultModel(tl("items/alien_head"));
            REGISTER.register("old_war_stealthkit", OldWarStealthkitItem::new).defaultModel(tl("items/old_war_stealthkit"));
            REGISTER.register("tri_tip_dagger", TriTipDaggerItem::new).defaultModel(tl("items/tri_tip_dagger"));
            REGISTER.register("stun_grenade", StunGrenadeItem::new).defaultModel(tl("items/stun_grenade"));
            REGISTER.register("warbanner", WarbannerItem::new).defaultModel(tl("items/warbanner"));
            REGISTER.register("the_crowdfunder", TheCrowdFunderItem::new).defaultModel(tl("items/the_crowdfunder"));
            REGISTER.register("sticky_bomb", StickyBombItem::new).defaultModel(tl("items/sticky_bomb"));
            REGISTER.register("lunar_coin", LunarCoinItem::new);
        }
    }


}
