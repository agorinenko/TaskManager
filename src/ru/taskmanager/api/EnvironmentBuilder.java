package ru.taskmanager.api;

/**
 * Created by agorinenko on 15.11.2017.
 */
public class EnvironmentBuilder {
    private String env;

    public EnvironmentBuilder setEnv(String env) {
        this.env = env;
        return this;
    }

    public EnvironmentParameter build(){
        return new EnvironmentParameter(this.env);
    }
}
