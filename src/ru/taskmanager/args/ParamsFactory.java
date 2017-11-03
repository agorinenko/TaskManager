package ru.taskmanager.args;

import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.args.params.*;


public class ParamsFactory {
    public BaseParam create(String arg) throws StringIsEmptyException, CorruptedParamException {
        if(KeyValueParam.isKeyValueParam(arg)){
            StringParam stringParam = new StringParam(arg);
            String stringValue = stringParam.getStringValue();

            if(IntegerParam.isIntegerParam(stringValue)){
                return new IntegerParam(arg);
            }

            if(IntegerParam.isBooleanParam(stringValue)){
                return new BooleanParam(arg);
            }

            return stringParam;
        } else {
            return new CommandParam(arg);
        }
    }

    public KeyValueParam createKeyValueParam(String key, String stringValue) throws StringIsEmptyException, CorruptedParamException {
        KeyValueParam result;
        if(IntegerParam.isIntegerParam(stringValue)){
            result = new IntegerParam(key, stringValue);
        } else if(IntegerParam.isBooleanParam(stringValue)){
            result = new BooleanParam(key, stringValue);
        }
        else {
            result = new StringParam(key, stringValue);
        }

        return result;
    }
}
