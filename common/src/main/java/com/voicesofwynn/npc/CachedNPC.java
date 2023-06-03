package com.voicesofwynn.npc;

import com.voicesofwynn.core.wrappers.VOWLocation;
import com.voicesofwynn.utils.LocationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class CachedNPC {

    private final String rawName;
    private final VOWLocation location;
    private final double distanceToPlayer;

    public CachedNPC(String rawName, VOWLocation location){
        this.rawName = rawName;
        this.location = location;
        this.distanceToPlayer = Minecraft.getInstance().player != null ?
                location.distanceTo(LocationUtils.convertToVOWLocation(Minecraft.getInstance().player.position())) :
                0.0D;
    }

    public String getRawName() {
        return rawName;
    }

    public VOWLocation getLocation() {
        return location;
    }

    public double getDistanceToPlayer() {
        return distanceToPlayer;
    }
}
