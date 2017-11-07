package args.api.mappers;

import args.TestUtils;
import org.junit.Test;
import ru.taskmanager.api.Version;
import ru.taskmanager.api.mappers.LocalVersionManager;
import ru.taskmanager.errors.CommandException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class LocalVersionManagerTest {
    @Test
    public void create() throws IOException, CommandException {
        LocalVersionManager manager = new LocalVersionManager();
        Path newFile = manager.createFile(java.util.UUID.randomUUID().toString().replace('-', '0')+ ".txt");

        assertTrue(Files.exists(newFile));

        manager.setBaseDir("out/dir1");
        Path newFile2 = manager.createFile(java.util.UUID.randomUUID().toString().replace('-', '0')+ ".txt");

        assertTrue(Files.exists(newFile2));
    }
    @Test
    public void select() throws IOException {
        LocalVersionManager manager = new LocalVersionManager();

        Path testMigration = args.TestUtils.generateTestMigration(null);
        assertTrue(null != testMigration);

        List<Version> list = manager.select();
        assertTrue(list.size() > 0);

        manager.setBaseDir("out/dir1");

        Path testMigration2 = TestUtils.generateTestMigration("out:out/dir1");
        assertTrue(null != testMigration2);

        List<Version> list2 = manager.select();
        assertTrue(list2.size() > 0);
    }
}
