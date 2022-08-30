package io.v4guard.plugin.core;

import io.v4guard.plugin.core.check.CheckManager;
import io.v4guard.plugin.core.mode.v4GuardMode;
import io.v4guard.plugin.core.socket.BackendConnector;
import io.v4guard.plugin.core.tasks.CompletableTaskManager;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class v4GuardCore {

    private static v4GuardCore INSTANCE;
    private final File folder;

    private CompletableTaskManager completableTaskManager;
    private BackendConnector backendConnector;
    private CheckManager checkManager;

    public static final String pluginVersion = "1.1.2";

    private boolean debug = false;
    private v4GuardMode pluginMode = v4GuardMode.UNKNOWN;
    private Logger logger;

    public v4GuardCore(v4GuardMode mode) throws IOException, URISyntaxException {
        INSTANCE = this;
        this.pluginMode = mode;
        initializeLogger();
        this.folder = new File("plugins/v4Guard/");
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }

        String debugProperty = System.getProperty("v4guardDebug", "false");
        if(debugProperty.equalsIgnoreCase("true") || debugProperty.equalsIgnoreCase("false")){
            this.debug = Boolean.valueOf(debugProperty);
            if(this.isDebugEnabled()){
                logger.log(Level.WARNING,"Debugging mode has been activated via java arguments");
            }
        } else {
            logger.log(Level.WARNING,"The debugging argument (-Dv4guardDebug) has an invalid value: " + System.getProperty("v4guardDebug", "NOT FOUND") + ". Debugging is now disabled.");
        }

        this.completableTaskManager = new CompletableTaskManager();
        this.backendConnector = new BackendConnector();
        this.checkManager = new CheckManager();

    }

    public Logger getLogger() {
        return logger;
    }

    public File getDataFolder() {
        return folder;
    }

    public CompletableTaskManager getCompletableTaskManager() {
        return completableTaskManager;
    }

    public BackendConnector getBackendConnector() {
        return backendConnector;
    }

    public CheckManager getCheckManager() {
        return checkManager;
    }

    public static v4GuardCore getInstance() {
        return INSTANCE;
    }

    public v4GuardMode getPluginMode() {
        return pluginMode;
    }

    public void setPluginMode(v4GuardMode pluginMode) {
        this.pluginMode = pluginMode;
    }

    public boolean isDebugEnabled() {
        return debug;
    }

    public void initializeLogger(){
        logger = Logger.getLogger("v4Guard");
        logger.setUseParentHandlers(true);
    }
}
