package ru.taskmanager.args.params;

import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.StringUtils;

public abstract class BaseParam {
    protected String key;
    protected String stringValue;

    public BaseParam(String key, String stringValue){
        this.key = key;
        this.stringValue = stringValue;
    }

    public BaseParam(String arg) throws StringIsEmptyException, CorruptedParamException {
        arg = arg.trim();
        checkString(arg);

        parseArg(arg);
    }

    public abstract Object getValue();

    protected abstract void parseArg(String arg) throws CorruptedParamException, StringIsEmptyException;

    protected String sanitizeString(String str) throws StringIsEmptyException {
        str = StringUtils.trim(str, " ");
        checkString(str);

        return str;
    }

    protected void checkString(String str) throws StringIsEmptyException {
        if(StringUtils.isNullOrEmpty(str)) throw new StringIsEmptyException("key=" + (null == key ? "undefined" : key));
    }

    public String getStringValue() throws StringIsEmptyException {
        return sanitizeString(stringValue);
    }

    public String getDefaultOrStringValue(String defaultValue){
        try {
            return getStringValue();
        } catch (StringIsEmptyException e) {
            return defaultValue;
        }
    }

    public String getKey() throws StringIsEmptyException {
        return sanitizeString(key);
    }
}
