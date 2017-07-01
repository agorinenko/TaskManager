package ru.taskmanager.utils;

import ru.taskmanager.config.Settings;
import ru.taskmanager.errors.ConfigurationException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class SettingsUtils {

    public static String getBaseSettingsDir(){
        Path path = Paths.get(StringUtils.trimEnd(System.getProperty("user.dir"), "//"), "settings");

        return path.toString();
    }

    public static String getBaseScriptDir(){
        String dbFolder = SettingsUtils.getSettingsValue("db.folder");
        dbFolder = StringUtils.trimEnd(dbFolder, "//");

        Path path = Paths.get(getBaseSettingsDir(), "assets", dbFolder);
        return path.toString();
    }

    public static String getScriptPath(String script){
        String baseScriptDir = SettingsUtils.getBaseScriptDir();
        Path path = Paths.get(baseScriptDir, script);
        return path.toString();
    }

    public static String getOutScriptDir(){
        String outFolder = SettingsUtils.getSettingsValue("out");
        outFolder = StringUtils.trimEnd(outFolder, "//");

        Path path = Paths.get(outFolder);
        return path.toString();
    }

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
