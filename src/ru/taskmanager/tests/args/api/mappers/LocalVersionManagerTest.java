package args.api.mappers;

import args.TestUtils;
import org.junit.Test;
import ru.taskmanager.api.Version;
import ru.taskmanager.api.mappers.LocalVersionManager;
import ru.taskmanager.args.ParamsFactory;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.StringIsEmptyException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class LocalVersionManagerTest {
    @Test
    public void create() throws CommandException, StringIsEmptyException, CorruptedParamException {
        ParamsFactory factory = new ParamsFactory();
        List<KeyValueParam> params = new ArrayList<>();
        KeyValueParam param = factory.createKeyValueParam("out", "out/dir1");
        params.add(param);
        LocalVersionManager manager = new LocalVersionManager(params);
        Path newFile = manager.createFile(java.util.UUID.randomUUID().toString().replace('-', '0')+ ".txt");

        assertTrue(Files.exists(newFile));

        Path newFile2 = manager.createFile(java.util.UUID.randomUUID().toString().replace('-', '0')+ ".txt");

        assertTrue(Files.exists(newFile2));
    }
    @Test
    public void select1() throws IOException {
        List<KeyValueParam> params = new ArrayList<>();
        LocalVersionManager manager = new LocalVersionManager(params);

        Path testMigration = args.TestUtils.generateTestMigration(null);
        assertTrue(null != testMigration);

        List<Version> list = manager.select();
        assertTrue(list.size() > 0);
    }
    @Test
    public void select2() throws IOException, StringIsEmptyException, CorruptedParamException {
        ParamsFactory factory = new ParamsFactory();
        List<KeyValueParam> params = new ArrayList<>();
        KeyValueParam param = factory.createKeyValueParam("out", "out/dir1");
        params.add(param);

        LocalVersionManager manager = new LocalVersionManager(params);

        Path testMigration2 = TestUtils.generateTestMigration("out:out/dir1");
        assertTrue(null != testMigration2);

        List<Version> list2 = manager.select();
        assertTrue(list2.size() > 0);
    }
}
