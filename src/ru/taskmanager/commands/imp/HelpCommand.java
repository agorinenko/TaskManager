package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.config.Settings;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.StringUtils;

import java.util.List;
import java.util.Map;

public class HelpCommand extends SafetyCommand {

    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        Map<String, String> items = null;
        try {
            items = Settings.getInstance().getEntityByKey("help");
        } catch (ConfigurationException e) {
        }
        String helpText = null != items ? items.get("text") : "";
        helpText = StringUtils.trim(helpText, " ");
        helpText = StringUtils.replaceAllSpecialSonstants(helpText);
        result.setMessage(helpText);
        return result;
    }
}
