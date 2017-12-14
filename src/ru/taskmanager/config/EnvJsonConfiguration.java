package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.StringUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class EnvJsonConfiguration extends JsonConfiguration {
    private Map<String, Map<String, Object>> dictionary;

    public EnvJsonConfiguration(Path jsonPath) throws ConfigurationException {
        super(jsonPath);
        this.dictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    @Override
    public Object getEntityByKey(String key) {
        return dictionary.get(key);
    }

    @Override
    public Set<String> getKeys() {
        return dictionary.keySet();
    }

    @Override
    public boolean containsKey(String key) {
        return dictionary.containsKey(key);
    }

    @Override
    public void load() throws ConfigurationException {
        dictionary.clear();

        JsonReader jsonReader;
        try {
            jsonReader = Json.createReader(new FileInputStream(this.jsonPath.toFile().getAbsoluteFile()));
        } catch (FileNotFoundException e) {
            throw new ConfigurationException("File doesn't exist");
        }
        try {
            JsonObject json = jsonReader.readObject();
            Set<Map.Entry<String, JsonValue>> parentSet = json.entrySet();
            for (Map.Entry<String, JsonValue> env : parentSet) {
                String envKey = env.getKey();
                if (env.getValue() instanceof JsonObject) {
                    HashMap map = new HashMap();
                    JsonObject envJson = (JsonObject) env.getValue();
                    Set<Map.Entry<String, JsonValue>> set = envJson.entrySet();

                    for (Map.Entry<String, JsonValue> entry : set) {
                        String key = entry.getKey();
                        if (!StringUtils.isNullOrEmpty(key) && !map.containsKey(key)) {
                            JsonValue value = entry.getValue();
                            Object realValue = detectValue(value);
                            if (null != realValue) {
                                map.put(key, realValue);
                            }
                        }
                    }
                    dictionary.put(envKey, map);
                }
            }
        } finally {
            jsonReader.close();
        }
    }
}
