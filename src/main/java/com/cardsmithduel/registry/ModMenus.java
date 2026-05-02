package com.cardsmithduel.registry;

import com.cardsmithduel.CardsmithDuel;
import com.cardsmithduel.backpack.CardBackpackMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(
            ForgeRegistries.MENU_TYPES, CardsmithDuel.MODID
    );

    public static final RegistryObject<MenuType<CardBackpackMenu>> CARD_BACKPACK_MENU = MENUS.register(
            "card_backpack_menu",
            () -> IForgeMenuType.create(CardBackpackMenu::new)
    );
}
