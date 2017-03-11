package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.StringUtils;

import java.util.List;

public class DbCommand extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";

        KeyValueParam commandParam  = ListUtils.getKeyValueParam(params, "c");

        if(null == commandParam){
            throw new CommandException("Parameter 'c' is required. For example, c:init, c:migrate, c:rollback");
        }
        String command;
        try {
            command = commandParam.getStringValue();
        } catch (StringIsEmptyException e) {
            throw new CommandException("Parameter 'c' is null or empty");
        }


        result.setMessage(resultMessage);
        return result;
    }
}
