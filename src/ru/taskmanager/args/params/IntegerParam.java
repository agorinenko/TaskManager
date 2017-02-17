package ru.taskmanager.args.params;

import ru.taskmanager.args.errors.CorruptedParamException;
import ru.taskmanager.args.errors.StringIsEmptyException;

public class IntegerParam extends KeyValueParam {

    public IntegerParam(String arg) throws StringIsEmptyException, CorruptedParamException {
        super(arg);
    }

    @Override
    public Object getValue() {
        return Integer.parseInt(stringValue);
    }

    public int getIntValue(){
        return (int)getValue();
    }
}
