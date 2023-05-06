package com.kuollutkissa.mastodonmod;

import com.kuollutkissa.mastodonmod.entity.ModEntities;
import com.kuollutkissa.mastodonmod.item.ModItems;
import com.kuollutkissa.mastodonmod.screen.ModScreenHandlers;
import com.kuollutkissa.mastodonmod.util.ModSoundEvents;
import com.kuollutkissa.mastodonmod.world.ModWorldGen;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MastodonMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "mastodonmod";
	public static final Logger LOGGER = LoggerFactory.getLogger("mastodonmod");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.registerModItems();
		ModSoundEvents.registerModSoundEvents();
		ModScreenHandlers.registerScreenHandlers();
		ModEntities.registerEntityAttributes();
		ModWorldGen.applyWorldgen();
		LOGGER.info("Hello Fabric world!");
	}
}