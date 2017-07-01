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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DbCommand {
    @Test
    public void init() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:init" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

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

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void status() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:status" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void delete() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {

        String testMigration = generateTestMigration();
        assertTrue(testMigration.length() > 0);

        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:delete", "v:20170522124649370" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    private String generateTestMigration() {
        ParamsManager manager;
        try {
            manager = new ParamsManager(new String[]{ "generate", "t:sql", "n:test", "stamp:true" });
            Executor executor = new Executor(manager);
            List<CommandResult> result = executor.execute();
            Path fileName = (Path) result.get(0).getMetaData("file");

            String s = "Hello World!";
            byte data[] = s.getBytes();

            Path file = Files.write(fileName, data, StandardOpenOption.WRITE);

            return file.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return "";
        }
    }
}
