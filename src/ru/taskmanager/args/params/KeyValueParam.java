package ru.taskmanager.args.params;

import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.StringIsEmptyException;

public abstract class KeyValueParam extends BaseParam {
    private static final String argSeparator = ":";

    public KeyValueParam(String key, String stringValue) {
        super(key, stringValue);
    }

    public static boolean isKeyValueParam(String arg){
        int separatorIndex = arg.indexOf(argSeparator);

        return separatorIndex > 0;
    }

    public static boolean isIntegerParam(String stringValue){
        try {
            Integer.parseInt(stringValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBooleanParam(String stringValue){
        return stringValue.equalsIgnoreCase("true") ||
                stringValue.equalsIgnoreCase("false");
    }

    public KeyValueParam(String arg) throws StringIsEmptyException, CorruptedParamException {
        super(arg);
    }

    private void setStringValue(String value){
        this.stringValue = value;
    }

    @Override
    protected void parseArg(String arg) throws CorruptedParamException, StringIsEmptyException {
        int separatorIndex = arg.indexOf(argSeparator);
        if(!isKeyValueParam(arg)){
            throw new CorruptedParamException(arg);
        }

        key = arg.substring(0, separatorIndex);
        key = sanitizeString(key);

        setStringValue(arg.substring(separatorIndex + 1));
        setStringValue(sanitizeString(stringValue));
    }
}
