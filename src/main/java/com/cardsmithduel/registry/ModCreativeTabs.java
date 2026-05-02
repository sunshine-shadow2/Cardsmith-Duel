package com.cardsmithduel.registry;

import com.cardsmithduel.CardsmithDuel;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, CardsmithDuel.MODID
    );

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable("creativemode_tab.cardsmithduel.example_tab"))
            .icon(() -> ModItems.BLANK_CARD.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.BLANK_CARD.get());
                output.accept(ModItems.SWIFTNESS_CARD.get());
                output.accept(ModItems.STRENGTH_CARD.get());
                output.accept(ModItems.REGENERATION_CARD.get());
                output.accept(ModItems.CARD_BACKPACK.get());
            }).build()
    );

    public static void register() {
    }
}