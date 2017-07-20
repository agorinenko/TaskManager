package ru.taskmanager.tests.args;

import ru.taskmanager.api.Version;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.RequiredParamException;
import ru.taskmanager.errors.StringIsEmptyException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestUtils {
    public static Path generateTestMigration(String outParameter) {
        ParamsManager manager;
        try {
            manager = new ParamsManager(new String[]{ "generate", "t:sql", "n:test", "stamp:true", outParameter });
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

    public static String getVersionTimestampString(Path testMigration){
        String file = testMigration.getFileName().toString();
        Version version = new Version(file);
        String versionTimestamp = version.getVersionTimestampString();

        return versionTimestamp;
    }

    public static void execPushCommand(String vParameter, String outParameter) throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:push", vParameter, outParameter, "c:test comment" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }
}
