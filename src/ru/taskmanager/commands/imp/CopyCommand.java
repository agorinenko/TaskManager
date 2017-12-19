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
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        String from = getStringParam(params, "from");
        String to = getStringParam(params, "to");

        if(StringUtils.isNullOrEmpty(to)){
            throw new CommandException("Parameter \"to\" is null or empty");
        }

        if(StringUtils.isNullOrEmpty(from)){
            throw new CommandException("Parameter \"from\" is null or empty");
        }

        TreeCopier copier;
        try {
            copier = new TreeCopier(Paths.get(from), Paths.get(to));
            copier.copy();
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }

        return createSingleSuccessResult("The copy from \"" + from + "\" to \"" + to + "\" was successful");
    }
}
