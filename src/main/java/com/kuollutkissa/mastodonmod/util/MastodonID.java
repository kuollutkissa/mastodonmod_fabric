package com.kuollutkissa.mastodonmod.util;

import com.kuollutkissa.mastodonmod.MastodonMod;
import net.minecraft.util.Identifier;

public class MastodonID extends Identifier {
    public MastodonID(String path) {
        super(MastodonMod.MODID, path);
    }
}
