package ru.taskmanager.commands;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.config.EnvironmentVariables;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.StringUtils;

import java.util.List;

public abstract class SafetyCommand implements Command {

    public abstract CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException;

    @Override
    public CommandResult execute(List<KeyValueParam> params) {
        try {
            KeyValueParam envParam = ListUtils.getKeyValueParam(params, "env");
            String env = null;
            if(null != envParam){
                try {
                    env = envParam.getStringValue();
                } catch (StringIsEmptyException e) {
                }
            }

            if(!StringUtils.isNullOrEmpty(env)){
                try {
                    EnvironmentVariables.getInstance().setEnvironmentParameter(env);
                } catch (ConfigurationException e) {
                }
            }

            return safetyExecute(params);
        } catch (CommandException ex){
            return new ErrorResult(ex);
        }
    }
}
