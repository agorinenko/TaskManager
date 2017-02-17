package ru.taskmanager.args.params;

import ru.taskmanager.args.errors.CorruptedParamException;
import ru.taskmanager.args.errors.StringIsEmptyException;

public class StringParam extends KeyValueParam {
    public StringParam(String arg) throws StringIsEmptyException, CorruptedParamException {
        super(arg);
    }

    @Override
    public Object getValue() {
        return stringValue;
    }
}
