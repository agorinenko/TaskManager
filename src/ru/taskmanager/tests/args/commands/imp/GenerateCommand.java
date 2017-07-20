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

import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class GenerateCommand {
    @Test
    public void exec() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        ParamsManager manager = new ParamsManager(new String[]{ "g", "t:sql" });
        testMigration(manager);
    }

    @Test
    public void exec0() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        ParamsManager manager = new ParamsManager(new String[]{ "g" });
        testMigration(manager);
    }

    @Test
    public void exec1() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        ParamsManager manager = new ParamsManager(new String[]{ "generate", "t:sql", "n:test" });
        testMigration(manager);
    }

    @Test
    public void exec2() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        ParamsManager manager = new ParamsManager(new String[]{ "generate", "t:sql" });
        testMigration(manager);
    }

    @Test
    public void exec3() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        ParamsManager manager = new ParamsManager(new String[]{ "generate", "t:log", "n:test", "stamp:true" });
        testMigration(manager);
    }

    @Test
    public void exec4() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        ParamsManager manager = new ParamsManager(new String[]{ "g", "out:out/dir2" });
        testMigration(manager);
    }

    private void testMigration(ParamsManager manager) throws StringIsEmptyException, InstantiationException, ClassNotFoundException, IllegalAccessException, ConfigurationException, RequiredParamException {
        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();
        Path fileName = (Path) result.get(0).getMetaData("file");

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
        assertTrue(fileName.toString().length() > 0);
    }
}
