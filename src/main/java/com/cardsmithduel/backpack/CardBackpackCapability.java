package com.cardsmithduel.backpack;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CardBackpackCapability implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<CardBackpack> BACKPACK_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    private CardBackpack backpack = null;
    private final LazyOptional<CardBackpack> lazyBackpack = LazyOptional.of(this::getOrCreateBackpack);

    @Nonnull
    private CardBackpack getOrCreateBackpack() {
        if (backpack == null) {
            backpack = new CardBackpack();
        }
        return backpack;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == BACKPACK_CAPABILITY ? lazyBackpack.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return getOrCreateBackpack().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getOrCreateBackpack().deserializeNBT(nbt);
    }
}
