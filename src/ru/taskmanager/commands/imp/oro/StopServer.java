package ru.taskmanager.commands.imp.oro;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.errors.CommandException;

import java.util.List;

/**
 * Created by agorinenko on 21.09.2017.
 */
public class StopServer extends SafetyCommand {
    @Override
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        return createSingleSuccessResult("");
    }
}
