package com.voicesofwynn.utils;

import com.voicesofwynn.core.wrappers.VOWLocation;
import net.minecraft.world.phys.Vec3;

public class LocationUtils {

    public static VOWLocation convertToVOWLocation(Vec3 pos) {
        return new VOWLocation(pos.x, pos.y, pos.z);
    }

}
