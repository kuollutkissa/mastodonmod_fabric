package com.kuollutkissa.mastodonmod.screen;

import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<MastodonInventoryScreenHandler> MASTODON_INVENTORY_SCREEN_HANDLER;


    public static void registerScreenHandlers() {
        MASTODON_INVENTORY_SCREEN_HANDLER = new ScreenHandlerType<>(MastodonInventoryScreenHandler::new);
    }
}
