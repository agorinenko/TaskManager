package ru.taskmanager.config;

public class ConfManager {
    private static ConfManager ourInstance = new ConfManager();

    public static ConfManager getInstance() {
        return ourInstance;
    }

    private ConfManager() {
    }
}
