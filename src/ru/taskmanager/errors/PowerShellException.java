package ru.taskmanager.errors;

/**
 * Created by agorinenko on 28.09.2017.
 */
public class PowerShellException  extends Exception {
    public PowerShellException() { super(); }
    public PowerShellException(String message) { super(message); }
    public PowerShellException(String message, Throwable cause) { super(message, cause); }
    public PowerShellException(Throwable cause) { super(cause); }
}
