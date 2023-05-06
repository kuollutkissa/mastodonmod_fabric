package com.kuollutkissa.mastodonmod.entity.client;

import com.kuollutkissa.mastodonmod.util.MastodonID;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import com.kuollutkissa.mastodonmod.entity.custom.MastodonEntity;
public class MastodonRenderer extends GeoEntityRenderer<    MastodonEntity> {
    public MastodonRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MastodonModel());
    }

    @Override
    public Identifier getTexture(MastodonEntity animatable) {
        if(animatable.isChested()) {
            return new MastodonID("textures/entity/mastodon_chested.png");
        }
        return new MastodonID("textures/entity/mastodon2.png");
    }

    @Override
    protected void applyRotations(MastodonEntity animatable, MatrixStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        if(animatable.isBaby()){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
    }
}
