package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by agorinenko on 15.11.2017.
 */
public abstract class JsonConfigurationManager extends ConfigurationManager {
    protected JsonConfigurationManager() throws ConfigurationException {
    }

    @Override
    protected Configuration createConfiguration() throws ConfigurationException {
        Path path = Paths.get(getAppConfig());
        return new JsonConfiguration(path);

    }
}
