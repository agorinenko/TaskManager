package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.SettingsUtils;

import java.util.Map;

/**
 * Created by agorinenko on 15.11.2017.
 */
public abstract class ConfigurationManager {
    protected Configuration configuration;
    protected boolean configurationIsLoad;

    protected ConfigurationManager() throws ConfigurationException {
        configuration = createConfiguration();

        if(!configurationIsLoad) {
            configuration.load();

            configurationIsLoad = true;
        }
    }

    protected abstract Configuration createConfiguration() throws ConfigurationException;
    protected abstract String getAppConfig();

    protected String getBaseDir(){
        return SettingsUtils.getBaseSettingsDir();
    }

    public Map<String, Object> getEntityByKey(String key) throws ConfigurationException {
        Map<String, Object> item = (Map<String, Object>) configuration.getEntityByKey(key);

        if(null == item){
            throw new ConfigurationException("Item " + key + " not found");
        }

        return item;
    }
}
