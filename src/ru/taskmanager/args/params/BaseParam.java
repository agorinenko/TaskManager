package ru.taskmanager.args.params;

import ru.taskmanager.args.errors.CorruptedParamException;
import ru.taskmanager.args.errors.StringIsEmptyException;

public abstract class BaseParam {
    protected String key;
    protected String stringValue;

    public BaseParam(String arg) throws StringIsEmptyException, CorruptedParamException {
        arg = arg.trim();
        checkString(arg);

        parseArg(arg);
    }

    public abstract Object getValue();

    protected abstract void parseArg(String arg) throws CorruptedParamException, StringIsEmptyException;

    protected String sanitizeString(String str) throws StringIsEmptyException {
        str = str.trim();
        checkString(str);

        return str;
    }

    protected void checkString(String str) throws StringIsEmptyException {
        if(str.isEmpty()) throw new StringIsEmptyException();
    }

    public String getStringValue() throws StringIsEmptyException {
        return sanitizeString(stringValue);
    }

    public String getKey() throws StringIsEmptyException {
        return sanitizeString(key);
    }
}
