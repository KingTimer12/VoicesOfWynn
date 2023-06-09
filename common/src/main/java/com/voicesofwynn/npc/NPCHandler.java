package com.voicesofwynn.npc;

import com.voicesofwynn.VOWCommon;
import com.voicesofwynn.core.wrappers.VOWLocation;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.decoration.ArmorStand;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class NPCHandler {

    private static final Map<String, VOWLocation> cachedNPC = new HashMap<>();

    public static Optional<VOWLocation> find(String rawName) {
        return Optional.ofNullable(cachedNPC.get(rawName));
    }

    public static void registerAndUpdate(String rawName, VOWLocation location) {
        cachedNPC.put(rawName, location);
        VOWCommon.getInstance().getLogger().locLog("[VOW-NPC] - Register And Update " + rawName);
    }

    public static void removeEntities(ArmorStand armorStand) {
        String name = Objects.requireNonNull(armorStand.getCustomName()).getString();
        String rawName = Objects.requireNonNull(ChatFormatting.stripFormatting(name)).replaceAll("[^a-z?\\d]", "");
        if (!cachedNPC.containsKey(rawName)) return;
        cachedNPC.remove(rawName);
        VOWCommon.getInstance().getLogger().locLog("[VOW-NPC] - Remove Entity " + rawName);
    }

}
