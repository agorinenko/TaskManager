package ru.taskmanager;

import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.config.ConfigurationManager;
import ru.taskmanager.config.Settings;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.StringUtils;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            ParamsManager manager = new ParamsManager(new String[]{ "HelpCommand" });
            Executor executor = new Executor(manager);
            List<CommandResult> result = executor.execute();
            String message = result.get(0).getMessage();

            System.out.print(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
