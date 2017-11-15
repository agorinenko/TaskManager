package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.imp.oro.StartServer;
import ru.taskmanager.commands.imp.oro.StopServer;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StringUtils;

import java.util.List;

/**
 * Created by agorinenko on 21.09.2017.
 */
public class OroCommand extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        KeyValueParam commandParam  = ListUtils.getKeyValueParam(params, "o");

        String forExampleText = (String) SettingsUtils.getSettingsValue("oroCommandsHelp");
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
        if(command.equalsIgnoreCase("start")) {
            safetyCommand = new StartServer();
        } else if(command.equalsIgnoreCase("stop")) {
            safetyCommand = new StopServer();
        } else {
            throw new CommandException("Command '"+ command +"' not found. " + forExampleText);
        }

        return safetyCommand.safetyExecute(params);
    }
}
