package args;

import ru.taskmanager.api.Version;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.*;
import ru.taskmanager.utils.StringUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
            String s = "CREATE TABLE " + tableName + " (\n" +
                    "  ID INT PRIMARY KEY\n" +
                    ");";
            byte data[] = s.getBytes();

            Path file = Files.write(fileName, data, StandardOpenOption.WRITE);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getVersionTimestampString(Path testMigration){
        String file = testMigration.getFileName().toString();
        Version version = new Version(file);
        String versionTimestamp = version.getVersionTimestampString();

        return versionTimestamp;
    }

    public static void execPushCommand(String vParameter, String outParameter)
            throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException, UniqueParamException {

        List<String> pList = new ArrayList();
        if(!StringUtils.isNullOrEmpty(vParameter)){
            pList.add(vParameter);
        }

        if(!StringUtils.isNullOrEmpty(outParameter)){
            pList.add(outParameter);
        }

        String[] stringArray = pList.toArray(new String[0]);

        execPushCommand(stringArray);
    }

    public static void execPushCommand(String[] outParameters)
            throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException, UniqueParamException {

        List<String> pList = new LinkedList<String>(Arrays.asList(outParameters));
        pList.add("db");
        pList.add("o:push");

        String[] stringArray = pList.toArray(new String[0]);
        ParamsManager manager = new ParamsManager(stringArray);

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }
}
