package com.voicesofwynn.forge.events;

import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientJoinServer {

    @SubscribeEvent
    public void drawLast(PlayerLoggedInEvent event) {
        System.out.println("[VOW-AB] - Logged!");
    }

}
