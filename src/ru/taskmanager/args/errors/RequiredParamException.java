package ru.taskmanager.args.errors;

public class RequiredParamException extends Exception {
    public RequiredParamException() { super(); }
    public RequiredParamException(String message) { super(message); }
    public RequiredParamException(String message, Throwable cause) { super(message, cause); }
    public RequiredParamException(Throwable cause) { super(cause); }
}
