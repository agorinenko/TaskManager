package ru.taskmanager.utils;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.StringIsEmptyException;

import java.util.List;

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
}

