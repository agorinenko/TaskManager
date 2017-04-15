package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.commands.imp.db.Init;
import ru.taskmanager.commands.imp.db.Push;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;

import java.util.List;

public class DbCommand extends SafetyCommand {

    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        KeyValueParam commandParam  = ListUtils.getKeyValueParam(params, "o");
        String forExampleText = "For example, o:init, o:push, o:rollback";

        if(null == commandParam){
            throw new CommandException("Parameter 'o' is required. " + forExampleText);
        }
        String command;
        try {
            command = commandParam.getStringValue();
        } catch (StringIsEmptyException e) {
            throw new CommandException("Parameter 'o' is null or empty");
        }

        SafetyCommand safetyCommand;
        if(command.equalsIgnoreCase("init")) {
            safetyCommand = new Init();
        } else if(command.equalsIgnoreCase("push")) {
            safetyCommand = new Push();
        }
        else {
            throw new CommandException("Command '"+ command +"' not found. " + forExampleText);
        }

        return safetyCommand.safetyExecute(params);
    }
}