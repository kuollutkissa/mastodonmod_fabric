package com.kuollutkissa.mastodonmod.entity;

import com.kuollutkissa.mastodonmod.util.MastodonID;
import com.kuollutkissa.mastodonmod.util.RegMessage;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;
import com.kuollutkissa.mastodonmod.entity.custom.MastodonEntity;

public class ModEntities {
    public static final EntityType<MastodonEntity> MASTODONT = Registry.register(
            Registry.ENTITY_TYPE, new MastodonID("mastodont"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MastodonEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());
    public static void registerEntityAttributes() {
        FabricDefaultAttributeRegistry.register(MASTODONT, MastodonEntity.setAttributes());
        RegMessage.regMessage("entity attributes");
    }
}
