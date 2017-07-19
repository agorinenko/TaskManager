package ru.taskmanager.tests.args.commands.imp;

import org.junit.Test;
import ru.taskmanager.api.Version;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.RequiredParamException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.StringUtils;

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
        Path testMigration = generateTestMigration();
        assertTrue(null != testMigration);

        Path testMigration2 = generateTestMigration();
        assertTrue(null != testMigration2);

        exec_push(null);
    }

    @Test
    public void push_target_version() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {
        Path testMigration = generateTestMigration();
        assertTrue(null != testMigration);

        String file = testMigration.getFileName().toString();
        Version version = new Version(file);
        String versionTimestamp = version.getVersionTimestampString();

        exec_push("v:" + versionTimestamp);
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
    public void delete_target_version() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {

        Path testMigration = generateTestMigration();
        assertTrue(null != testMigration);

        String file = testMigration.getFileName().toString();
        Version version = new Version(file);
        String versionTimestamp = version.getVersionTimestampString();

        exec_push("v:" + versionTimestamp);

        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:delete", "v:" + versionTimestamp });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void delete_last_version() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException {

        Path testMigration = generateTestMigration();
        assertTrue(null != testMigration);

        String file = testMigration.getFileName().toString();
        Version version = new Version(file);
        String versionTimestamp = version.getVersionTimestampString();

        exec_push("v:" + versionTimestamp);

        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:delete" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    private void exec_push(String vParameter) throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:push", vParameter, "c:test comment" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    private Path generateTestMigration() {
        ParamsManager manager;
        try {
            manager = new ParamsManager(new String[]{ "generate", "t:sql", "n:test", "stamp:true" });
            Executor executor = new Executor(manager);
            List<CommandResult> result = executor.execute();
            Path fileName = (Path) result.get(0).getMetaData("file");

            String tableName = "test.\"" + java.util.UUID.randomUUID().toString().replace('-', '0') + "\"";
            String s = "DROP TABLE IF EXISTS " + tableName + ";" +
                    "CREATE TABLE " + tableName + " (\n" +
                    "  ID  SERIAL PRIMARY KEY\n" +
                    ");";
            byte data[] = s.getBytes();

            Path file = Files.write(fileName, data, StandardOpenOption.WRITE);

            return file;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
