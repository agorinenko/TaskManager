package ru.taskmanager.utils;

import ru.taskmanager.Main;
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
                e.printStackTrace();
            }
        }
        Path path = Paths.get(StringUtils.trimEnd(base, "//"), "settings");

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
