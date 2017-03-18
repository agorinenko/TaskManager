package ru.taskmanager.utils;

import ru.taskmanager.config.Settings;
import ru.taskmanager.errors.ConfigurationException;

import java.util.Map;

public class SettingsUtils {

    public static String getSettingsOrDefaultValue(String key){
        String value = getSettingsValue(key);

        if(StringUtils.isNullOrEmpty(value)){
            value = getSettingsValue(key + ".default");
        }

        return value;
    }
    public static String getSettingsValue(String key){
        Map<String, String> items = null;
        try {
            items = Settings.getInstance().getEntityByKey(key);
        } catch (ConfigurationException e) {
        }
        String value = null != items ? StringUtils.trim(items.get("value"), " ") : "";

        return value;
    }
}
