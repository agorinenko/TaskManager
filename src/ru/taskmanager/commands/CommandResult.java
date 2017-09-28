package ru.taskmanager.commands;

import java.util.HashMap;
import java.util.Map;

public abstract class CommandResult {
    protected String message;
    protected Map<String, Object> metaData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public abstract boolean isResult();

    public void addMetaData(String key, Object value){
        if(null == this.metaData){
            this.metaData = new HashMap<>();
        }

        this.metaData.put(key, value);
    }

    public Object getMetaData(String key){
        if(null == this.metaData){
            return null;
        }

        return this.metaData.get(key);
    }
}
