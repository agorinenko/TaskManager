package ru.taskmanager.api;

/**
 * Created by agorinenko on 15.11.2017.
 */
public final class EnvironmentParameter {
    private final String env;

    public EnvironmentParameter(String env) {
        this.env = env;
    }

    public String getEnv() {
        return env;
    }
}
