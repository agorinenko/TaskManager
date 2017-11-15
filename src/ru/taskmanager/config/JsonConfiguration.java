package ru.taskmanager.config;

import org.w3c.dom.Document;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.StringUtils;
import ru.taskmanager.utils.XmlUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by agorinenko on 15.11.2017.
 */
public class JsonConfiguration implements Configuration {
    private Map<String, Map<String, Object>> dictionary;
    private final Path jsonPath;

    public JsonConfiguration(Path jsonPath) throws ConfigurationException {
        if (!Files.exists(jsonPath)) {
            throw new ConfigurationException("File doesn't exist");
        }

        this.dictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        this.jsonPath = jsonPath;
    }

    @Override
    public Object getEntityByKey(String key) {
        return dictionary.get(key);
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
                JsonObject envJson = (JsonObject)env.getValue();
                Set<Map.Entry<String, JsonValue>> set = envJson.entrySet();

                Map<String, Object> map = new HashMap();
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
        } finally {
            jsonReader.close();
        }
    }

    private Object detectValue(JsonValue value){
        if(null == value){
            return null;
        }

        JsonValue.ValueType type = value.getValueType();

        if(type == JsonValue.ValueType.NULL){
            return null;
        } else if(type == JsonValue.ValueType.ARRAY){
            throw new NotImplementedException();
        } else if(type == JsonValue.ValueType.OBJECT){
            throw new NotImplementedException();
        }else if(type == JsonValue.ValueType.STRING){
            return ((JsonString)value).getString();
        }else if(type == JsonValue.ValueType.NUMBER){
            boolean isIntegral = ((JsonNumber)value).isIntegral();
            if(isIntegral){
                return ((JsonNumber)value).longValue();
            }

            return ((JsonNumber)value).doubleValue();
        }else if(type == JsonValue.ValueType.TRUE || type == JsonValue.ValueType.FALSE){
            return Boolean.valueOf(value.toString());
        }

        throw new NotImplementedException();
    }
}
