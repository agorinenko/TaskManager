package ru.taskmanager.tests.args.api.mappers;

import org.junit.Test;
import ru.taskmanager.api.LocalVersion;
import ru.taskmanager.api.mappers.LocalVersionManager;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class LocalVersionManagerTest {
    @Test
    public void select() throws IOException {
        LocalVersionManager manager = new LocalVersionManager();
        List<LocalVersion> list = manager.select();

        assertTrue(list.size() > 0);
    }
}
