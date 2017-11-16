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
            String message;
            if(result.size()>0) {
                message = result.get(0).getMessage();
            } else {
                message = "No results";
            }

            if(null != message) {
                System.out.print(message);
                System.out.print(System.lineSeparator());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
