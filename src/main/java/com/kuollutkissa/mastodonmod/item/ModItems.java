package com.kuollutkissa.mastodonmod.item;


import com.kuollutkissa.mastodonmod.entity.ModEntities;
import com.kuollutkissa.mastodonmod.util.MastodonID;
import com.kuollutkissa.mastodonmod.util.RegMessage;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;


public class ModItems {
    public static final Item MASTODONT_SPAWN_EGG =registerItem("mastodont_spawn_egg",
            new SpawnEggItem(ModEntities.MASTODONT, 0x686e62, 0xfffadf,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new MastodonID(name), item);
    }
    public static void registerModItems() {
        RegMessage.regMessage("items");
    }
}
