package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;

public class Commands extends XmlConfigurationManager {
    private static volatile Commands instance;

    private Commands() throws ConfigurationException {
    }

    public static Commands getInstance() throws ConfigurationException {
        Commands localInstance = instance;
        if (localInstance == null) {
            synchronized (Commands.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Commands();
                }
            }
        }
        return localInstance;
    }

    @Override
    protected String getAppConfig() {
        return getBaseDir() + "\\commands.xml";
    }
}
