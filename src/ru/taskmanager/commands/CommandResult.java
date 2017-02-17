package ru.taskmanager.commands;

public abstract class CommandResult {
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public abstract boolean isResult();
}
