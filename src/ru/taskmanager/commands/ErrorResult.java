package ru.taskmanager.commands;

import ru.taskmanager.errors.CommandException;

public class ErrorResult extends CommandResult {
    private CommandException exception;

    public ErrorResult(CommandException exception) {
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return( null == message || message.isEmpty()) ? exception.getMessage() : message;
    }

    @Override
    public boolean isResult() {
        return false;
    }
}
