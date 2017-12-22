package ru.taskmanager.utils;

import ru.taskmanager.Main;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.config.Settings;
import ru.taskmanager.errors.ConfigurationException;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class SettingsUtils {

    public static String getHome(){
        String home = "";
        Map<String, String> env = System.getenv();
        if (env.containsKey("TM_HOME")){
            home = env.get("TM_HOME");
        }

        return home;
    }

    public static String getBaseSettingsDir(){
        String base = getHome();
        if (StringUtils.isNullOrEmpty(base)){
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

    public static String getOutScriptDir(List<KeyValueParam> params){
        String outFolder = (String) SettingsUtils.getSettingsOrParamValue(params, "out");
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

    public static Object getSettingsOrParamValue(List<KeyValueParam> params, String key){
        KeyValueParam param = ListUtils.getKeyValueParam(params, key);
        if(null != param){
            return param.getValue();
        }

        return getSettingsValue(key);
    }

    public static Object getSettingsValue(String key){
        return getSettingsInternal(key);
    }

    private static Object getSettingsInternal(String key){
        Object value = null;
        Map<String, Object> items;
        try {
            items = Settings.getInstance().getEntityByKey(key);
            value = null != items ? items.get("value") : null;
        } catch (ConfigurationException ex) {}

        return value;
    }
}
