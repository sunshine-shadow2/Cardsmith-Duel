package com.cardsmithduel.registry;

import com.cardsmithduel.CardsmithDuel;
import com.cardsmithduel.backpack.CardBackpackItem;
import com.cardsmithduel.content.cards.BlankCard;
import com.cardsmithduel.content.cards.SwiftnessCard;
import com.cardsmithduel.content.cards.StrengthCard;
import com.cardsmithduel.content.cards.RegenerationCard;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, CardsmithDuel.MODID
    );

    public static final RegistryObject<Item> BLANK_CARD = ITEMS.register("blank_card", BlankCard::new);
    public static final RegistryObject<Item> SWIFTNESS_CARD = ITEMS.register("swiftness_card", SwiftnessCard::new);
    public static final RegistryObject<Item> STRENGTH_CARD = ITEMS.register("strength_card", StrengthCard::new);
    public static final RegistryObject<Item> REGENERATION_CARD = ITEMS.register("regeneration_card", RegenerationCard::new);
    public static final RegistryObject<Item> CARD_BACKPACK = ITEMS.register("card_backpack", CardBackpackItem::new);

    public static void register() {
    }
}