package com.voicesofwynn;

import com.voicesofwynn.core.utils.VOWLog;
import org.slf4j.Logger;

public class CoreLogger extends VOWLog {

    private final Logger logger;

    public CoreLogger(Logger logger) {
        this.logger = logger;
        instance = this;
    }

    @Override
    public void locLog(String str) {
        logger.info(str);
    }

    @Override
    public void locWarn(String str) {
        logger.warn(str);
    }

    @Override
    public void locError(String str) {
        logger.error(str);
    }

    public void locDebug(String str) { logger.debug(str);}

}
