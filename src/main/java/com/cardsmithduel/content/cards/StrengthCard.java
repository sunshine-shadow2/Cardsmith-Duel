package com.cardsmithduel.content.cards;

import com.cardsmithduel.common.BaseCard;
import com.cardsmithduel.api.CardEffect;
import com.cardsmithduel.content.effects.StrengthEffect;

public class StrengthCard extends BaseCard {
    private static final CardEffect EFFECT = new StrengthEffect();

    public StrengthCard() {
        super(EFFECT, 1);
    }
}