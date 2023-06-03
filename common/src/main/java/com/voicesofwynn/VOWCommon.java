package com.voicesofwynn;

import com.voicesofwynn.core.VOWCore;
import com.voicesofwynn.core.VOWThread;
import com.voicesofwynn.core.loadmanager.LoadManager;
import com.voicesofwynn.provider.VOWProvider;
import com.voicesofwynn.utils.FileResourceUtils;
import net.minecraft.client.Minecraft;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class VOWCommon {

    public static final String MOD_ID = "voicesofwynn";

    private static final VOWCommon instance = new VOWCommon();

    private boolean wynnServer = false;

    private File folderMod;
    private CoreLogger logger;
    private ModLoader modLoader;

    public void enable(ModLoader modLoader) {
        this.folderMod = new File(Minecraft.getInstance().gameDirectory, MOD_ID);
        this.logger = new CoreLogger(LoggerFactory.getLogger(MOD_ID));
        this.modLoader = modLoader;

        new VOWThread(folderMod, modLoader, logger).start();

        logger.locLog("[VOW-"+modLoader.name()+"] Init version 1.0.0 (MC: 1.19.4)");
    }

    public File getFolderMod() {
        return folderMod;
    }

    public CoreLogger getLogger() {
        return logger;
    }

    public boolean isWynnServer() {
        return wynnServer;
    }

    public void setWynnServer(boolean wynnServer) {
        this.wynnServer = wynnServer;
    }

    public ModLoader getModLoader() {
        return modLoader;
    }

    public static VOWCommon getInstance() {
        return instance;
    }

    public static enum ModLoader {
        FORGE, FABRIC
    }
}