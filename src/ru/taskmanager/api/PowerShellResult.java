package ru.taskmanager.api;

public class PowerShellResult  implements java.io.Serializable{
    private final String successResult;
    private final String errorResult;

    public PowerShellResult(String successResult, String errorResult) {
        this.successResult = successResult;
        this.errorResult = errorResult;
    }

    public String getSuccessResult() {
        return successResult;
    }

    public String getErrorResult() {
        return errorResult;
    }
}
