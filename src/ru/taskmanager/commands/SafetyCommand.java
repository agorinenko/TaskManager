package ru.taskmanager.commands;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.CommandException;

import java.util.List;

public abstract class SafetyCommand implements Command {

    public abstract CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException;

    @Override
    public CommandResult execute(List<KeyValueParam> params) {
        try {
            return safetyExecute(params);
        } catch (CommandException ex){
            return new ErrorResult(ex);
        }
    }
}
