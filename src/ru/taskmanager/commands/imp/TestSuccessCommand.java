package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.StringIsEmptyException;

import java.util.List;

public class TestSuccessCommand extends SafetyCommand {
    @Override
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        try {
            return createSingleSuccessResult(params.get(0).getStringValue());
        } catch (StringIsEmptyException e) {
            throw new CommandException(e);
        }
    }
}
