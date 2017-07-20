package ru.taskmanager;

import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            ParamsManager manager = new ParamsManager(args);

            Executor executor = new Executor(manager);
            List<CommandResult> result = executor.execute();
            String message = result.get(0).getMessage();

            System.out.print(message);
            System.out.print(System.lineSeparator());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
