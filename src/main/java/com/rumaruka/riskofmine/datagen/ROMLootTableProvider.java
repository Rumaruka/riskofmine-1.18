package com.rumaruka.riskofmine.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.MethodsReturnNonnullByDefault;
import com.rumaruka.riskofmine.RiskOfMine;
import com.rumaruka.riskofmine.datagen.chests.ChestLootTableROM;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.rumaruka.riskofmine.RiskOfMine.logger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ROMLootTableProvider extends LootTableProvider {
    private final DataGenerator generator;
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public ROMLootTableProvider(DataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        ImmutableList.Builder<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> builder = new ImmutableList.Builder<>();
        builder.add(Pair.of(ChestLootTableROM::new, LootContextParamSets.CHEST));
        return builder.build();
    }


    @Override
    public String getName() {
        return super.getName() + ":" + RiskOfMine.MODID;
    }

    public void run(HashCache cache) {
        Path path = this.generator.getOutputFolder();
        Map<ResourceLocation, LootTable> map = Maps.newHashMap();
        this.getTables().forEach((pair_) -> {
            pair_.getFirst().get().accept((resourceLocation2_, builder_) -> {
                if (map.put(resourceLocation2_, builder_.setParamSet(pair_.getSecond()).build()) != null) {
                    throw new IllegalStateException("Duplicate loot table " + resourceLocation2_);
                }
            });
        });
        ValidationContext validationtracker = new ValidationContext(LootContextParamSets.ALL_PARAMS, (resourceLocation3_) -> {
            return null;
        }, map::get);

        validate(map, validationtracker);

        Multimap<String, String> multimap = validationtracker.getProblems();
        if (!multimap.isEmpty()) {
            multimap.forEach((string_, string1_) -> {
                logger.warn("Found validation problem in " + string_ + ": " + string1_);
            });
            throw new IllegalStateException("Failed to validate loot tables, see logs");
        } else {
            map.forEach((resourceLocation1_, lootTable1_) -> {
                Path path1 = createPath(path, resourceLocation1_);

                try {
                    DataProvider.save(GSON, cache, LootTables.serialize(lootTable1_), path1);
                } catch (IOException ioexception) {
                    logger.error("Couldn't save loot table {}", path1, ioexception);
                }

            });
        }
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {

    }

    private static Path createPath(Path pathIn, ResourceLocation id) {
        return pathIn.resolve("data/" + id.getNamespace() + "/loot_tables/" + id.getPath() + ".json");
    }
}


