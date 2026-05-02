package com.cardsmithduel.content.cards;

import com.cardsmithduel.common.BaseCard;
import com.cardsmithduel.api.CardEffect;
import com.cardsmithduel.content.effects.RegenerationEffect;

public class RegenerationCard extends BaseCard {
    private static final CardEffect EFFECT = new RegenerationEffect();

    public RegenerationCard() {
        super(EFFECT, 1);
    }
}