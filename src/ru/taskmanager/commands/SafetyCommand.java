package ru.taskmanager.commands;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.config.EnvironmentVariables;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class SafetyCommand implements Command {

    public abstract List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException;

    @Override
    public List<CommandResult> execute(List<KeyValueParam> params) {
        try {
            return safetyExecute(params);
        } catch (CommandException ex){
            List<CommandResult> result = new ArrayList<>(1);
            result.add(new ErrorResult(ex));
            return result;
        }
    }

    protected List<CommandResult> createSingleSuccessResult(String message){
        List<CommandResult> result = new ArrayList<>(1);
        SuccessResult successResult = new SuccessResult();
        successResult.setMessage(message);
        result.add(successResult);

        return result;
    }

    protected String getStringParam(List<KeyValueParam> params, String key, String defaultValue){
        String val = getStringParam(params, key);
        if(StringUtils.isNullOrEmpty(val)){
            val = defaultValue;
        }

        return val;
    }

    protected String getStringParam(List<KeyValueParam> params, String key){
        KeyValueParam param = ListUtils.getKeyValueParam(params, key);
        String val = "";
        if(null != param){
            try {
                val = param.getStringValue();
            } catch (StringIsEmptyException e) {}
        }

        return val;
    }
}
