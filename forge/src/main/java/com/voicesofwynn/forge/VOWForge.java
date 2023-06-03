package com.voicesofwynn.forge;

import com.voicesofwynn.VOWCommon;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VOWCommon.MOD_ID)
public class VOWForge {

    public VOWForge() {
        //It's not ready yet
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
    }

    public void setup(FMLClientSetupEvent event) {
        VOWCommon.getInstance().enable(VOWCommon.ModLoader.FORGE);
    }

}