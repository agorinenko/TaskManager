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

            return stringParam;
        } else {
            return new CommandParam(arg);
        }
    }
}
