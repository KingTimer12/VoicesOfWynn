package com.voicesofwynn.mixin;

import com.voicesofwynn.VOWCommon;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientHandshakePacketListenerImpl.class)
public abstract class MixinServerConnectListener {

    @Inject(at = @At("RETURN"), method = "authenticateServer")
    private void onSuccess(String serverHash, CallbackInfoReturnable<Component> cir) {
        String serverIP = serverHash.toLowerCase();
        if (serverIP.startsWith("play.wynncraft")
                || serverIP.startsWith("media.wynncraft")
                || serverIP.startsWith("beta.wynncraft")
                || serverIP.startsWith("lobby.wynncraft")) {
            VOWCommon.getInstance().getLogger().locLog("[VOW-"+VOWCommon.getInstance().getModLoader().name()+"] Joined Live Wynncraft server");
            VOWCommon.getInstance().setWynnServer(true);
        }
    }

}
