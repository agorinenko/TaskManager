package ru.taskmanager.utils;

import ru.taskmanager.Main;
import ru.taskmanager.api.EnvironmentParameter;
import ru.taskmanager.config.EnvironmentVariables;
import ru.taskmanager.config.Settings;
import ru.taskmanager.errors.ConfigurationException;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class SettingsUtils {

    public static String getBaseSettingsDir(){
        String base = "";

        Map<String, String> env = System.getenv();
        if (env.containsKey("TM_HOME")){
            base = env.get("TM_HOME");
        } else {
            try {
                File codeSource = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                base = codeSource.getParent();
            } catch (URISyntaxException e) {
            }
        }
        Path path = Paths.get(StringUtils.trimEnd(base, "//"), "settings");

        return path.toString();
    }

    public static String getBaseScriptDir(){
        String dbFolder = (String) SettingsUtils.getSettingsValue("db.folder");
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
        String outFolder = (String) SettingsUtils.getSettingsValue("out");
        outFolder = StringUtils.trimEnd(outFolder, "//");

        Path path = Paths.get(outFolder);
        return path.toString();
    }

    public static Object getSettingsOrDefaultValue(String key){
        Object value = getSettingsValue(key);

        if(null == value){
            value = getSettingsValue(key + ".default");
        }

        return value;
    }
    public static Object getSettingsValue(String key){
        Map<String, Object> items;
        Object value = null;
        try {
            EnvironmentParameter env = EnvironmentVariables.getInstance().getEnvironmentParameter();
            if(null != env) {
                items = EnvironmentVariables.getInstance().getEntityByKey(env.getEnv());

                if (items.containsKey(key)) {
                    value = null != items ? items.get(key) : null;
                } else {
                    value = getSettings(key);
                }
            } else {
                value = getSettings(key);
            }
        } catch (ConfigurationException e) {
        }

        return value;
    }

    public static Object getSettings(String key){
        Object value = null;
        Map<String, Object> items;
        try {
            items = Settings.getInstance().getEntityByKey(key);
            value = null != items ? items.get("value") : null;
        } catch (ConfigurationException ex) {}

        return value;
    }
}
