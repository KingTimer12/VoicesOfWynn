package com.voicesofwynn.fabric;

import com.voicesofwynn.VOWCommon;
import net.fabricmc.api.ModInitializer;

public class VOWFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        VOWCommon.getInstance().enable(VOWCommon.ModLoader.FABRIC);
    }

}