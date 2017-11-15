package ru.taskmanager.config;

import ru.taskmanager.api.EnvironmentBuilder;
import ru.taskmanager.api.EnvironmentParameter;
import ru.taskmanager.errors.ConfigurationException;

/**
 * Created by agorinenko on 15.11.2017.
 */
public class EnvironmentVariables extends JsonConfigurationManager {
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

    private EnvironmentParameter environmentParameter;

    public void setEnvironmentParameter(String env){
        environmentParameter = new EnvironmentBuilder()
                .setEnv(env)
                .build();
    }

    public EnvironmentParameter getEnvironmentParameter(){
        return environmentParameter;
    }

    @Override
    protected String getAppConfig() {
        return "env.json";
    }
}
