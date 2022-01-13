package com.rumaruka.riskofmine.common.cap.lunar;

import com.rumaruka.riskofmine.common.cap.lunar.data.Lunar;
import com.rumaruka.riskofmine.init.ROMCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.jetbrains.annotations.NotNull;
import ru.timeconqueror.timecore.common.capability.CoffeeCapability;
import ru.timeconqueror.timecore.common.capability.owner.CapabilityOwner;
import ru.timeconqueror.timecore.common.capability.owner.serializer.CapabilityOwnerSerializer;

import javax.annotation.Nullable;

public class ROMLunar extends CoffeeCapability<Entity> implements ILunar {

    public final Lunar lunar = container("lunar", new Lunar());

    private final Player player;

    public ROMLunar(Player player) {
        this.player = player;
    }

    @NotNull
    @Override
    public Capability<? extends CoffeeCapability<Entity>> getCapability() {
        return ROMCapability.LUNAR;
    }

    @NotNull
    @Override
    public CapabilityOwnerSerializer<Entity> getOwnerSerializer() {
        return CapabilityOwner.ENTITY.getSerializer();
    }

    @Override
    public void sendChangesToClients(@NotNull SimpleChannel simpleChannel, @NotNull Object o) {
        simpleChannel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), o);
    }

    public void detectAndSendChanges() {
        detectAndSendChanges(player.level, player);
    }

    public void sendAllData() {
        sendAllData(player.level, player);
    }

    @Nullable
    public static ROMLunar from(@Nullable Player player) {
        if (player != null) {
            LazyOptional<ROMLunar> cap = player.getCapability(ROMCapability.LUNAR);

            if (cap.isPresent()) {
                return cap.orElseThrow(IllegalStateException::new);
            }
        }

        return null;
    }
}
