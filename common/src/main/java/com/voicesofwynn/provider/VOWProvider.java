package com.voicesofwynn.provider;

import com.voicesofwynn.VOWCommon;
import com.voicesofwynn.core.interfaces.IFunctionProvider;
import com.voicesofwynn.core.wrappers.PlayEvent;
import com.voicesofwynn.core.wrappers.VOWLocation;
import com.voicesofwynn.npc.NPCHandler;
import com.voicesofwynn.utils.LocationUtils;
import net.minecraft.client.Minecraft;

import java.io.File;

public class VOWProvider implements IFunctionProvider {

    @Override
    public void playFileSound(File file, PlayEvent playEvent) {
        VOWCommon.getInstance().getLogger().locDebug("[VOW-"+VOWCommon.getInstance().getModLoader().name()+"] Play File Sound " + file.getName());
    }

    @Override
    public VOWLocation getNpcLocationFromName(String name) {
        return NPCHandler.find(name).orElse(null);
    }

    @Override
    public VOWLocation getPlayerLocation() {
        if (Minecraft.getInstance().player == null) return null;
        return LocationUtils.convertToVOWLocation(Minecraft.getInstance().player.getEyePosition());
    }

}

