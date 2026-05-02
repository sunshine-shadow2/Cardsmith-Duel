package com.cardsmithduel.backpack;

import com.cardsmithduel.common.BaseCard;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class CardBackpack {
    public static final int SLOT_COUNT = 27;

    private final ItemStackHandler inventory = new ItemStackHandler(SLOT_COUNT) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof BaseCard;
        }
    };

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.getItem() instanceof BaseCard;
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        ListTag listTag = new ListTag();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putByte("Slot", (byte) i);
                stack.save(itemTag);
                listTag.add(itemTag);
            }
        }
        tag.put("Items", listTag);
        return tag;
    }

    public void deserializeNBT(CompoundTag nbt) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
        ListTag listTag = nbt.getList("Items", 10);
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag itemTag = listTag.getCompound(i);
            int slot = itemTag.getByte("Slot") & 255;
            if (slot >= 0 && slot < inventory.getSlots()) {
                inventory.setStackInSlot(slot, ItemStack.of(itemTag));
            }
        }
    }
}
