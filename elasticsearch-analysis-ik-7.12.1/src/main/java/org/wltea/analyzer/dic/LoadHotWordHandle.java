package org.wltea.analyzer.dic;

import org.apache.logging.log4j.Logger;
import org.wltea.analyzer.help.ESPluginLoggerFactory;

/**
 * TODO  Copyright (c) yun lu 2021 Fau (viwcy4611@gmail.com), ltd
 */
public class LoadHotWordHandle implements Runnable {

    private static final Logger logger = ESPluginLoggerFactory.getLogger(LoadHotWordHandle.class.getName());

    @Override
    public void run() {
        logger.info("load hot words from mysql");
        Dictionary singleton = Dictionary.getSingleton();
        singleton.loadMySQLExtDict();
        singleton.loadMySQLStopWordDict();
        logger.info("\n");
    }
}
