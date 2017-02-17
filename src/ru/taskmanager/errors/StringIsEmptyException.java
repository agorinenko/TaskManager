package ru.taskmanager.errors;

public class StringIsEmptyException extends Exception {
    public StringIsEmptyException() { super(); }
    public StringIsEmptyException(String message) { super(message); }
    public StringIsEmptyException(String message, Throwable cause) { super(message, cause); }
    public StringIsEmptyException(Throwable cause) { super(cause); }
}
