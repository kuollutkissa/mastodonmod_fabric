package com.kuollutkissa.mastodonmod.entity.client;
import com.kuollutkissa.mastodonmod.util.MastodonID;
import com.kuollutkissa.mastodonmod.entity.custom.MastodonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class MastodonModel extends AnimatedGeoModel<MastodonEntity> {
    @Override
    public Identifier getModelResource(MastodonEntity animatable) {
        if(animatable.isChested()) {
            return new MastodonID("geo/mastodon_chested.geo.json");
        }
        return new MastodonID("geo/mastodon2.geo.json");
    }

    @Override
    public Identifier getTextureResource(MastodonEntity animatable) {
        if(animatable.isChested()) {
            return new MastodonID("textures/entity/mastodon_chested.png");
        }
        return new MastodonID("textures/entity/mastodon2.png");
    }

    @Override
    public Identifier getAnimationResource(MastodonEntity animatable) {
        return new MastodonID("animations/mastodon2.animation.json");
    }

}
