package com.voicesofwynn.provider;

import com.voicesofwynn.core.interfaces.IFunctionProvider;
import com.voicesofwynn.core.wrappers.PlayEvent;
import com.voicesofwynn.core.wrappers.VOWLocation;
import com.voicesofwynn.utils.LocationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

import java.io.File;

public class VOWProvider implements IFunctionProvider {

    @Override
    public void playFileSound(File file, PlayEvent playEvent) {

    }

    @Override
    public VOWLocation getNpcLocationFromName(String name) {
        return null;
    }

    @Override
    public VOWLocation getPlayerLocation() {
        if (Minecraft.getInstance().player == null) return null;
        return LocationUtils.convertToVOWLocation(Minecraft.getInstance().player.getEyePosition());
    }

}
