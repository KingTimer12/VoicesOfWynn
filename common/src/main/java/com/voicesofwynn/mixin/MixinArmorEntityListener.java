package com.voicesofwynn.mixin;

import com.voicesofwynn.VOWCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ClientPacketListener.class)
public abstract class MixinArmorEntityListener {

    @Inject(method = "handleAddEntity", at = @At("RETURN"))
    private void addEntity(ClientboundAddEntityPacket packet, CallbackInfo ci) {
        if (!VOWCommon.getInstance().isWynnServer()) return;
        if (!Minecraft.getInstance().isSameThread() || Minecraft.getInstance().level == null) return;
        Entity entity = Minecraft.getInstance().level.getEntity(packet.getId());
        getArmor(entity).filter(this::isArmorValid).ifPresent(armorStand -> {
            //TODO: Add armor stand in cache?
        });
    }

    @Inject(method = "handleTeleportEntity", at = @At("RETURN"))
    private void updateEntity(ClientboundTeleportEntityPacket packet, CallbackInfo ci) {
        if (!VOWCommon.getInstance().isWynnServer()) return;
        if (!Minecraft.getInstance().isSameThread() || Minecraft.getInstance().level == null) return;
        Entity entity = Minecraft.getInstance().level.getEntity(packet.getId());
        getArmor(entity).filter(this::isArmorValid).ifPresent(armorStand -> {
            //TODO: Add armor stand in cache?
        });
    }

    public Optional<ArmorStand> getArmor(Entity entity) {
        if (Minecraft.getInstance().level == null) return Optional.empty();
        if (entity instanceof ArmorStand) return Optional.of((ArmorStand) entity);
        return Optional.empty();
    }

    public boolean isArmorValid(ArmorStand armorStand) {
        if (Minecraft.getInstance().player == null) return false;
        if (armorStand == null || armorStand.getCustomName() == null) return false;
        if (!armorStand.isAlive() || (armorStand.isInvisible() && !armorStand.isCustomNameVisible())) return false;
        return !(armorStand.getEyePosition().distanceTo(Minecraft.getInstance().player.getEyePosition()) > 200);
        // TODO: Maybe add config change
    }

}
