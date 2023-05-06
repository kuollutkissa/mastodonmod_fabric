package com.kuollutkissa.mastodonmod.util;

import com.kuollutkissa.mastodonmod.MastodonMod;


public class RegMessage {
    public static void regMessage(String type) {
        MastodonMod.LOGGER.debug("Registering "+ type+ " for " + MastodonMod.MODID);
    }
}
