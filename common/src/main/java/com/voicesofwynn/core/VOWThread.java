package com.voicesofwynn.core;

import com.voicesofwynn.CoreLogger;
import com.voicesofwynn.VOWCommon;
import com.voicesofwynn.core.loadmanager.LoadManager;
import com.voicesofwynn.core.utils.VOWLog;
import com.voicesofwynn.provider.VOWProvider;
import com.voicesofwynn.utils.FileResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class VOWThread extends Thread {

    private final File folderMod;
    private final VOWCommon.ModLoader modLoader;
    private final CoreLogger logger;

    public VOWThread(File folderMod, VOWCommon.ModLoader modLoader, CoreLogger logger) {
        this.folderMod = folderMod;
        this.modLoader = modLoader;
        this.logger = logger;
    }

    @Override
    public void run() {
        VOWCore.init(new VOWProvider(), folderMod);

        LoadManager loadManager = new LoadManager();
        try {
            String dialogueName = "kingsrecruit";
            Optional<File> resourceFile = FileResourceUtils.getResourceAsFile("dialogues/"+dialogueName+".yml");
            if (resourceFile.isEmpty()) {
                logger.locError("[VOW-"+modLoader.name()+"] Couldn't find the dialogs inside the mod! URI's null.");
                return;
            }
            File dialoguesIn = resourceFile.get();
            File dialoguesOut = new File(this.folderMod.getAbsoluteFile(), "/" + dialogueName + "/dialogue.yml");
            logger.locLog("[VOW-"+modLoader.name()+"] In: " + dialoguesIn.getAbsolutePath() + " // Out: " + dialoguesOut.getAbsolutePath());

            loadManager.build(dialoguesIn,
                    dialoguesOut,
                    this.folderMod);
            logger.locLog("[VOW-"+modLoader.name()+"] Successfully transferred the dialogs!");

            loadManager.load(dialoguesOut);
            logger.locLog("[VOW-"+modLoader.name()+"] Successfully registered the dialogs!");
        } catch (IOException e) {
            logger.locError("[VOW-"+modLoader.name()+"] An exception error occurred!");
            e.printStackTrace();
        }
    }
}
