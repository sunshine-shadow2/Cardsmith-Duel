package com.cardsmithduel.backpack;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = com.cardsmithduel.CardsmithDuel.MODID)
public class BackpackEventHandler {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(com.cardsmithduel.CardsmithDuel.MODID, "card_backpack"), new ICapabilityProvider() {
                private final CardBackpackCapability provider = new CardBackpackCapability();

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return provider.getCapability(cap, side);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            return;
        }

        Player original = event.getOriginal();
        Player newPlayer = event.getEntity();

        original.revive();
        original.getCapability(CardBackpackCapability.BACKPACK_CAPABILITY).ifPresent(oldBackpack -> {
            newPlayer.getCapability(CardBackpackCapability.BACKPACK_CAPABILITY).ifPresent(newBackpack -> {
                CompoundTag nbt = oldBackpack.serializeNBT();
                newBackpack.deserializeNBT(nbt);
            });
        });
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
    }
}
