package ru.taskmanager.commands.imp;

import ru.taskmanager.FinalConstants;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.SettingsUtils;

import java.util.ArrayList;
import java.util.List;

public class InfoCommand extends SafetyCommand {
    @Override
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        List<CommandResult> results = new ArrayList<>();

        addSuccessResult(results, String.format("Version: %s", FinalConstants.Version));
        addSuccessResult(results, String.format("Home: %s", SettingsUtils.getHome()));
        addSuccessResult(results, String.format("Base settings dir: %s", SettingsUtils.getBaseSettingsDir()));
        addSuccessResult(results, String.format("Base script dir: %s", SettingsUtils.getBaseScriptDir(params)));
        addSuccessResult(results, String.format("Out script dir: %s", SettingsUtils.getOutScriptDir(params)));
        addSuccessResult(results, String.format("Params: %s", keyValueParamsToString(params)));

        return results;
    }

    private String keyValueParamsToString(List<KeyValueParam> params) throws CommandException {
        StringBuilder str = new StringBuilder(params.size());
        for (KeyValueParam kvp : params) {
            try {
                str.append(String.format("%s%s:%s", System.lineSeparator(), kvp.getKey(), kvp.getStringValue()));
            } catch (StringIsEmptyException e) {
                str.append(e.getMessage());
            }
        }
        return str.toString();
    }

    private boolean addSuccessResult(List<CommandResult> results, String message){
        SuccessResult successResult= new SuccessResult();
        successResult.setMessage(message);
        return results.add(successResult);
    }
}
