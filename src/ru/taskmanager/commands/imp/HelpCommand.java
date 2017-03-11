package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.config.Settings;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StringUtils;

import java.util.List;
import java.util.Map;

public class HelpCommand extends SafetyCommand {

    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String helpText = SettingsUtils.getSettingsValue("help");
        helpText = StringUtils.replaceAllSpecialConstants(helpText);
        result.setMessage(helpText);
        return result;
    }
}
