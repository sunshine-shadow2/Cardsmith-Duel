package com.cardsmithduel.content.effects;

import com.cardsmithduel.api.CardEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class SpeedEffect implements CardEffect {
    @Override
    public void apply(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 120 * 20, 0));
    }

    @Override
    public String getEffectId() {
        return "speed";
    }
}