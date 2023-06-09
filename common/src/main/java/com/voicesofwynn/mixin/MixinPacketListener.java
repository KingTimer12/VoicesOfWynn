package com.voicesofwynn.mixin;

import com.voicesofwynn.VOWCommon;
import com.voicesofwynn.core.registers.DialogueRegister;
import com.voicesofwynn.core.soundmanager.DefaultSoundManager;
import com.voicesofwynn.core.soundmanager.SoundManager;
import com.voicesofwynn.core.utils.LineUtils;
import com.voicesofwynn.core.wrappers.PlayEvent;
import com.voicesofwynn.core.wrappers.VOWLocation;
import com.voicesofwynn.npc.NPCHandler;
import com.voicesofwynn.utils.LocationUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(value = ClientPacketListener.class, priority = 900)
public abstract class MixinPacketListener {

    @Inject(method = "handleAddEntity", at = @At("RETURN"))
    private void addEntity(ClientboundAddEntityPacket packet, CallbackInfo ci) {
        if (!VOWCommon.getInstance().isWynnServer()) return;
        if (!Minecraft.getInstance().isSameThread() || Minecraft.getInstance().level == null) return;
        Entity entity = Minecraft.getInstance().level.getEntity(packet.getId());
        getArmor(entity).filter(this::isArmorValid).ifPresent(armorStand -> {
            String name = Objects.requireNonNull(armorStand.getCustomName()).getString();
            String rawName = Objects.requireNonNull(ChatFormatting.stripFormatting(name)).replaceAll("[^a-z?\\d]", "");
            VOWLocation location = LocationUtils.convertToVOWLocation(armorStand.getEyePosition());
            NPCHandler.registerAndUpdate(rawName, location);
        });
    }

    @Inject(method = "handleTeleportEntity", at = @At("RETURN"))
    private void updateEntity(ClientboundTeleportEntityPacket packet, CallbackInfo ci) {
        if (!VOWCommon.getInstance().isWynnServer()) return;
        if (!Minecraft.getInstance().isSameThread() || Minecraft.getInstance().level == null) return;
        Entity entity = Minecraft.getInstance().level.getEntity(packet.getId());
        getArmor(entity).filter(this::isArmorValid).ifPresent(armorStand -> {
            String name = Objects.requireNonNull(armorStand.getCustomName()).getString();
            String rawName = Objects.requireNonNull(ChatFormatting.stripFormatting(name)).replaceAll("[^a-z?\\d]", "");
            VOWLocation location = LocationUtils.convertToVOWLocation(armorStand.getEyePosition());
            NPCHandler.registerAndUpdate(rawName, location);
        });
    }

    @Inject(method = "handleRemoveEntities", at = @At("RETURN"))
    private void removeEntity(ClientboundRemoveEntitiesPacket packet, CallbackInfo ci) {
        if (!VOWCommon.getInstance().isWynnServer()) return;
        if (!Minecraft.getInstance().isSameThread() || Minecraft.getInstance().level == null) return;
        Set<ArmorStand> entities = packet.getEntityIds()
                .intStream()
                .mapToObj(id -> (ArmorStand)Minecraft.getInstance().level.getEntity(id))
                .collect(Collectors.toSet());
        entities.forEach(NPCHandler::removeEntities);
    }

    @Inject(method = "handleSystemChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/chat/ChatListener;handleSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"))
    private void onMessage(ClientboundSystemChatPacket packet, CallbackInfo ci) {
        if (!Minecraft.getInstance().isSameThread()) return;
        if (packet.content().getString().contains("[Voices of Wynn]") || packet.overlay()) return;
        String message = packet.content().getString();
        String name = getPlayerName(message);
        if (message.contains(name)) {
            message = message.replace(name, "soldier");
        }

        int endingIndex = message.indexOf("[");
        if (endingIndex == -1) return;
        message = message.replace(message.substring(0, endingIndex), "").replace("Press SHIFT to continue", "");

        String format = LineUtils.lineFromMessage(message);
        Map<String, DialogueRegister.Dialog> dialogMap = DialogueRegister.getInstance().getDialogs();
        if (!dialogMap.containsKey(format)) return;
        DialogueRegister.Dialog dialog = dialogMap.get(format);

        PlayEvent playEvent = new PlayEvent();
        playEvent.location = dialog.location;
        playEvent.fallOff = dialog.fallOff;

        SoundManager.getInstance().playSound(dialog.file.getPath(), playEvent); // Error
    }

    private Optional<ArmorStand> getArmor(Entity entity) {
        return (entity instanceof ArmorStand) ? Optional.of((ArmorStand) entity) : Optional.empty();
    }

    private boolean isArmorValid(ArmorStand armorStand) {
        if (Minecraft.getInstance().player == null) return false;
        if (armorStand == null || armorStand.getCustomName() == null) return false;
        if (!armorStand.isAlive() || (armorStand.isInvisible() && !armorStand.isCustomNameVisible())) return false;
        return !(armorStand.getEyePosition().distanceTo(Minecraft.getInstance().player.getEyePosition()) > 200);
        // TODO: Maybe add config change
    }

    private String getPlayerName(String eventMessageToString) {
        assert Minecraft.getInstance().player != null;
        String realName = Minecraft.getInstance().player.getName().getString();
        String[] segments = eventMessageToString.split("hoverEvent=HoverEvent\\{action=SHOW_TEXT, value='TextComponent\\{text='");
        if (segments.length <= 1) return realName;
        String name = segments[segments.length - 1].split("',")[0];
        if (name.contains("Previous")) return realName;
        return name.split("'")[0];

    }

}
