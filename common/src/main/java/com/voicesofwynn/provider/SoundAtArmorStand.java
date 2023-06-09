package com.voicesofwynn.provider;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class SoundAtArmorStand extends AbstractTickableSoundInstance {

    protected SoundAtArmorStand(SoundEvent soundEvent, SoundSource soundSource, RandomSource randomSource) {
        super(soundEvent, soundSource, randomSource);
    }

    @Override
    public void tick() {

    }
}
