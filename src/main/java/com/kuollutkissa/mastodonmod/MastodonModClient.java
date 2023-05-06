package com.kuollutkissa.mastodonmod;

import com.kuollutkissa.mastodonmod.entity.ModEntities;
import com.kuollutkissa.mastodonmod.entity.client.MastodonRenderer;
import com.kuollutkissa.mastodonmod.screen.MastodonInventoryScreen;
import com.kuollutkissa.mastodonmod.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class MastodonModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.MASTODON_INVENTORY_SCREEN_HANDLER, MastodonInventoryScreen::new);
        EntityRendererRegistry.register(ModEntities.MASTODONT, MastodonRenderer::new);
    }
}
