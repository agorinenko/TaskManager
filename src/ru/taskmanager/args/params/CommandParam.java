package ru.taskmanager.args.params;

import ru.taskmanager.args.errors.CorruptedParamException;
import ru.taskmanager.args.errors.StringIsEmptyException;

public class CommandParam extends BaseParam {

    public CommandParam(String arg) throws StringIsEmptyException, CorruptedParamException {
        super(arg);
    }

    @Override
    public Object getValue() {
        return stringValue;
    }

    @Override
    protected void parseArg(String arg) throws CorruptedParamException, StringIsEmptyException {
        String sanitizeString = sanitizeString(arg);

        key = sanitizeString;
        stringValue = sanitizeString;
    }
}
