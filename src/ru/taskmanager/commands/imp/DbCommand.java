package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.commands.imp.db.*;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StringUtils;

import java.util.List;

public class DbCommand extends SafetyCommand {

    @Override
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        KeyValueParam commandParam  = ListUtils.getKeyValueParam(params, "o");

        String forExampleText = (String) SettingsUtils.getSettingsValue("dbCommandsHelp");
        forExampleText = StringUtils.replaceAllSpecialConstants(forExampleText);
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
        } else if(command.equalsIgnoreCase("status")) {
            safetyCommand = new Status();
        } else if(command.equalsIgnoreCase("delete")) {
            safetyCommand = new Delete();
        } else if(command.equalsIgnoreCase("remove")) {
            safetyCommand = new Remove();
        } else {
            throw new CommandException("Command '"+ command +"' not found. " + forExampleText);
        }

        return safetyCommand.safetyExecute(params);
    }
}
