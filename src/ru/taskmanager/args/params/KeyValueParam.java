package ru.taskmanager.args.params;

import ru.taskmanager.args.errors.CorruptedParamException;
import ru.taskmanager.args.errors.StringIsEmptyException;

public abstract class KeyValueParam extends BaseParam {
    private static final String argSeparator = ":";

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

//    public boolean isIntegerParam(){
//        return this instanceof IntegerParam;
//    }

    public KeyValueParam(String arg) throws StringIsEmptyException, CorruptedParamException {
        super(arg);
    }

    @Override
    protected void parseArg(String arg) throws CorruptedParamException, StringIsEmptyException {
        int separatorIndex = arg.indexOf(argSeparator);
        if(!isKeyValueParam(arg)){
            throw new CorruptedParamException(arg);
        }

        key = arg.substring(0, separatorIndex);
        sanitizeString(key);

        stringValue = arg.substring(separatorIndex + 1);
        sanitizeString(stringValue);
    }
}
