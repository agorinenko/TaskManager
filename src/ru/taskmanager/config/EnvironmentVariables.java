package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;

/**
 * Created by agorinenko on 15.11.2017.
 */
public class EnvironmentVariables extends EnvJsonConfigurationManager {
    private static volatile EnvironmentVariables instance;

    private EnvironmentVariables() throws ConfigurationException {
    }

    public static EnvironmentVariables getInstance() throws ConfigurationException {
        EnvironmentVariables localInstance = instance;
        if (localInstance == null) {
            synchronized (EnvironmentVariables.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EnvironmentVariables();
                }
            }
        }

        return localInstance;
    }

    @Override
    protected String getAppConfig() {
        return "env.json";
    }
}
