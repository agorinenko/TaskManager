package ru.taskmanager.errors;


public class ConnectionManagerException extends Exception {
    public ConnectionManagerException() { super(); }
    public ConnectionManagerException(String message) { super(message); }
    public ConnectionManagerException(String message, Throwable cause) { super(message, cause); }
    public ConnectionManagerException(Throwable cause) { super(cause); }
}
