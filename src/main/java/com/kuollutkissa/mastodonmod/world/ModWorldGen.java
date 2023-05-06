package com.kuollutkissa.mastodonmod.world;

import com.kuollutkissa.mastodonmod.entity.ModEntities;
import com.kuollutkissa.mastodonmod.world.gen.ModEntitySpawn;

public class ModWorldGen {
    public static void applyWorldgen(){
        ModEntitySpawn.spawnMobEntities();
    }
}
