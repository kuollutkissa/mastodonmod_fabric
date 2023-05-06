package com.kuollutkissa.mastodonmod.util;


import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
    public static final SoundEvent MASTODON_AMBIENT = registerSoundEvent("mastodon_ambient");
    public static final SoundEvent MASTODON_HURT = registerSoundEvent("mastodon_hurt");
    public static final SoundEvent MASTODON_AMBIENT_NORLUND = registerSoundEvent("mastodon_ambient_norlund");
    public static final SoundEvent MASTODON_HURT_NORLUND = registerSoundEvent("mastodon_hurt_norlund");
    private static SoundEvent registerSoundEvent(String id) {
        return Registry.register(Registry.SOUND_EVENT, new MastodonID(id),
                new SoundEvent(new MastodonID(id)));
    }
    public static void registerModSoundEvents() {
        RegMessage.regMessage("sound events");
    }
}
