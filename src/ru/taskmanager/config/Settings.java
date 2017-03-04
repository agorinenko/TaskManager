package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;

public class Settings extends ConfigurationManager {
    private static volatile Settings instance;
    private Settings() throws ConfigurationException {
        load();
    }

    public static Settings getInstance() throws ConfigurationException {
        Settings localInstance = instance;
        if (localInstance == null) {
            synchronized (ConfigurationManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Settings();
                }
            }
        }
        return localInstance;
    }
    @Override
    public String getAppConfig() {
        return getBaseDir() + "\\settings.xml";
    }
}
