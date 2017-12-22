package args.commands.imp;

import org.junit.Test;

import args.*;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.*;

import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DbCommand {
    @Test
    public void init() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
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
    public void init2() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:init", "onlyschema:true" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void init3() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:init", "db.url:jdbc:postgresql://localhost:5432/", "db.name:test_db1", "db.user:postgres", "db.pwd:Bandit777rus", "db.separator:--SEP--" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void remove() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:remove" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void push0() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        Path testMigration = TestUtils.generateTestMigration(null);
        assertTrue(null != testMigration);

        Path testMigration2 = TestUtils.generateTestMigration(null);
        assertTrue(null != testMigration2);

        TestUtils.execPushCommand(null, null);
    }

    @Test
    public void push1() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        Path testMigration = TestUtils.generateTestMigration("out:out/dir2");
        assertTrue(null != testMigration);

        Path testMigration2 = TestUtils.generateTestMigration("out:out/dir2");
        assertTrue(null != testMigration2);

        TestUtils.execPushCommand(null, "out:out/dir2");
    }

    @Test
    public void push2() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        Path testMigration = TestUtils.generateTestMigration(null);
        assertTrue(null != testMigration);

        TestUtils.execPushCommand(new String[]{ "env:dev" });
    }

    @Test
    public void push_target_version0() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        Path testMigration = TestUtils.generateTestMigration(null);
        assertTrue(null != testMigration);
        String versionTimestamp = TestUtils.getVersionTimestampString(testMigration);

        TestUtils.execPushCommand("v:" + versionTimestamp, null);
    }

    @Test
    public void push_target_version1() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        Path testMigration = TestUtils.generateTestMigration("out:out/dir2");
        assertTrue(null != testMigration);
        String versionTimestamp = TestUtils.getVersionTimestampString(testMigration);

        TestUtils.execPushCommand("v:" + versionTimestamp, "out:out/dir2");
    }

    @Test
    public void status0() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
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
    public void status1() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:status", "out:out/dir2" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void delete_target_version0() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {

        Path testMigration = TestUtils.generateTestMigration(null);
        assertTrue(null != testMigration);
        String versionTimestamp = TestUtils.getVersionTimestampString(testMigration);

        TestUtils.execPushCommand("v:" + versionTimestamp, null);

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
    public void delete_target_version1() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {

        Path testMigration = TestUtils.generateTestMigration("out:out/dir2");
        assertTrue(null != testMigration);
        String versionTimestamp = TestUtils.getVersionTimestampString(testMigration);

        TestUtils.execPushCommand("v:" + versionTimestamp, "out:out/dir2");

        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:delete", "v:" + versionTimestamp, "out:out/dir2" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void delete_last_version0() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {

        Path testMigration = TestUtils.generateTestMigration(null);
        assertTrue(null != testMigration);
        String versionTimestamp = TestUtils.getVersionTimestampString(testMigration);

        TestUtils.execPushCommand("v:" + versionTimestamp, null);

        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:delete" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }
    @Test
    public void delete_last_version1() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {

        Path testMigration = TestUtils.generateTestMigration("out:out/dir2");
        assertTrue(null != testMigration);
        String versionTimestamp = TestUtils.getVersionTimestampString(testMigration);

        TestUtils.execPushCommand("v:" + versionTimestamp, "out:out/dir2");

        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:delete", "out:out/dir2" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }
}
