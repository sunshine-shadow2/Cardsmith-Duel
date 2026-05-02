package com.cardsmithduel.api;

import net.minecraft.world.entity.player.Player;

public interface CardEffect {
    void apply(Player player);
    String getEffectId();
}