package ru.taskmanager.errors;

public class CorruptedParamException extends Exception {
    public CorruptedParamException() { super(); }
    public CorruptedParamException(String message) { super(message); }
    public CorruptedParamException(String message, Throwable cause) { super(message, cause); }
    public CorruptedParamException(Throwable cause) { super(cause); }
}
