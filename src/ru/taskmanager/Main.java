package ru.taskmanager;

import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.output.ConsolePrinter;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            ParamsManager manager = new ParamsManager(args);

            Executor executor = new Executor(manager);
            executor.executeAndPrint(new ConsolePrinter());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
