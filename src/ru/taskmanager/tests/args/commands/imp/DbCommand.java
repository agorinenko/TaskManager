package ru.taskmanager.tests.args.commands.imp;

import org.junit.Test;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.RequiredParamException;
import ru.taskmanager.errors.StringIsEmptyException;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DbCommand {
    @Test
    public void init() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:init" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void push() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:push", "c:test comment" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }
}
