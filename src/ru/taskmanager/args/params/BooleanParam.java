package ru.taskmanager.args.params;

import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.StringIsEmptyException;

public class BooleanParam extends KeyValueParam {

    public BooleanParam(String arg) throws StringIsEmptyException, CorruptedParamException {
        super(arg);
    }

    @Override
    public Object getValue() {
        return Boolean.parseBoolean(stringValue);
    }

    public boolean getBooleanValue(){
        return (boolean)getValue();
    }
}
