package com.cardsmithduel.backpack;

import com.cardsmithduel.CardsmithDuel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;

public class CardBackpackItem extends Item implements MenuProvider {
    public CardBackpackItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            player.openMenu(this);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("item.cardsmithduel.card_backpack");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        LazyOptional<CardBackpack> backpackCap = player.getCapability(CardBackpackCapability.BACKPACK_CAPABILITY);
        return backpackCap.map(backpack -> new CardBackpackMenu(id, inventory, backpack.getInventory())).orElse(null);
    }
}
