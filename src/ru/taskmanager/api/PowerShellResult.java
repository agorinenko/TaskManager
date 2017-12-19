package ru.taskmanager.api;

public class PowerShellResult  implements java.io.Serializable{
    private final int successLines;
    private final int errorLines;

    public PowerShellResult(int successLines, int errorLines) {
        this.successLines = successLines;
        this.errorLines = errorLines;
    }

    public int getSuccessLines() {
        return successLines;
    }

    public int getErrorLines() {
        return errorLines;
    }
}
