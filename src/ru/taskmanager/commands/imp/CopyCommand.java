package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.utils.StringUtils;
import ru.taskmanager.utils.TreeCopier;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class CopyCommand extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        String from = GetStringParam(params, "from");
        String to = GetStringParam(params, "to");
        if(StringUtils.isNullOrEmpty(to)){
            throw new CommandException("Parameter \"to\" is null or empty");
        }

        TreeCopier copier;
        try {
            copier = new TreeCopier(Paths.get(from), Paths.get(to));
            copier.copy();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SuccessResult result = new SuccessResult();
        result.setMessage("The copy from \"" + from + "\" to \"" + to + "\" was successful" + System.lineSeparator());
        return result;
    }
}
