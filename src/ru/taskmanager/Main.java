package ru.taskmanager;

import ru.taskmanager.config.ConfigurationManager;
import ru.taskmanager.config.Settings;
import ru.taskmanager.errors.ConfigurationException;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            Map<String, String> items= Settings.getInstance().getEntityByKey("test");
            System.out.print(items);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
