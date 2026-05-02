package com.cardsmithduel;

import com.cardsmithduel.backpack.CardBackpackScreen;
import com.cardsmithduel.registry.ModCreativeTabs;
import com.cardsmithduel.registry.ModItems;
import com.cardsmithduel.registry.ModMenus;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CardsmithDuel.MODID)
public class CardsmithDuel {
    public static final String MODID = "cardsmithduel";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CardsmithDuel() {
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModMenus.MENUS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModCreativeTabs.CREATIVE_MODE_TABS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(ModMenus.CARD_BACKPACK_MENU.get(), CardBackpackScreen::new);
        }
    }
}