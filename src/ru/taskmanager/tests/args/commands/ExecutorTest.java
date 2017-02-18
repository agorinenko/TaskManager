package ru.taskmanager.tests.args.commands;

import org.junit.Test;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.args.params.StringParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.ErrorResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.commands.imp.TestSuccessCommand;
import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.RequiredParamException;
import ru.taskmanager.errors.StringIsEmptyException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExecutorTest {
    @Test
    public void executor() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException {
        ParamsManager manager = new ParamsManager(new String[]{ "TestSuccessCommand", "TestErrorCommand", "p1:Success", "p2:Error" });
        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertEquals(result.get(0).getMessage(), "Success");
        assertTrue(result.get(1) instanceof ErrorResult);
        assertEquals(result.get(1).getMessage(), "Error");
    }
}
