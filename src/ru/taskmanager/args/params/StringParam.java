package ru.taskmanager.args.params;

import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.StringIsEmptyException;

public class StringParam extends KeyValueParam {
    public StringParam(String arg) throws StringIsEmptyException, CorruptedParamException {
        super(arg);
    }

    public StringParam(String key, String stringValue) {
        super(key, stringValue);
    }

    @Override
    public Object getValue() {
        return stringValue;
    }
}
