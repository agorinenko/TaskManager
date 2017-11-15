package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;

public class Settings extends XmlConfigurationManager {
    private static volatile Settings instance;

    private Settings() throws ConfigurationException {
    }

    public static Settings getInstance() throws ConfigurationException {
        Settings localInstance = instance;
        if (localInstance == null) {
            synchronized (Settings.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Settings();
                }
            }
        }
        return localInstance;
    }
    @Override
    protected String getAppConfig() {
        return getBaseDir() + "\\settings.xml";
    }
}
