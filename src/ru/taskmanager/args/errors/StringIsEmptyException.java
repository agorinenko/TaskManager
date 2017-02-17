package ru.taskmanager.args.errors;

/**
 * Created by Home User on 21.01.2017.
 */
public class StringIsEmptyException extends Exception {
    public StringIsEmptyException() { super(); }
    public StringIsEmptyException(String message) { super(message); }
    public StringIsEmptyException(String message, Throwable cause) { super(message, cause); }
    public StringIsEmptyException(Throwable cause) { super(cause); }
}
