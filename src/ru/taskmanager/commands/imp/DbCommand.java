package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.commands.imp.db.Init;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;

import java.util.List;

public class DbCommand extends SafetyCommand {

    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        KeyValueParam commandParam  = ListUtils.getKeyValueParam(params, "c");
        String forExampleText = "For example, c:init, c:migrate, c:rollback";

        if(null == commandParam){
            throw new CommandException("Parameter 'c' is required. " + forExampleText);
        }
        String command;
        try {
            command = commandParam.getStringValue();
        } catch (StringIsEmptyException e) {
            throw new CommandException("Parameter 'c' is null or empty");
        }

        SafetyCommand safetyCommand;
        if(command.equalsIgnoreCase("init")) {
            safetyCommand = new Init();
        }
        else {
            throw new CommandException("Command '"+ command +"' not found. " + forExampleText);
        }

        return safetyCommand.safetyExecute(params);
    }
}
