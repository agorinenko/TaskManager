package ru.taskmanager.errors;

public class UniqueParamException extends Exception {
    public UniqueParamException() { super(); }
    public UniqueParamException(String message) { super(message); }
    public UniqueParamException(String message, Throwable cause) { super(message, cause); }
    public UniqueParamException(Throwable cause) { super(cause); }
}
