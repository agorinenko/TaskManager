package ru.taskmanager.utils;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.StringIsEmptyException;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ListUtils {
    public static KeyValueParam getKeyValueParam(List<KeyValueParam> params, String key){
        KeyValueParam param = params.stream().filter(i -> {
            try {
                return i.getKey().equalsIgnoreCase(key);
            } catch (StringIsEmptyException e) {
                return false;
            }
        }).findFirst().orElse(null);

        return param;
    }

    public static HashMap<String, Object> convertToHashMap(List<KeyValueParam> params){
        HashMap<String, Object> result = new HashMap<>();

        if(null != params && params.size()>0){
            for (KeyValueParam param : params){
                try {
                    result.put(param.getKey(), param.getValue());
                } catch (StringIsEmptyException e) {}
            }
        }
        return result;
    }
}

