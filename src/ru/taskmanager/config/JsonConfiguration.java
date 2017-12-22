package ru.taskmanager.config;

import ru.taskmanager.errors.ConfigurationException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import javax.json.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by agorinenko on 15.11.2017.
 */
public abstract class JsonConfiguration implements Configuration {

    protected final Path jsonPath;

    public JsonConfiguration(Path jsonPath) throws ConfigurationException {
        if (!Files.exists(jsonPath)) {
            throw new ConfigurationException("File doesn't exist");
        }

        this.jsonPath = jsonPath;
    }


    protected Object detectValue(JsonValue value){
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
