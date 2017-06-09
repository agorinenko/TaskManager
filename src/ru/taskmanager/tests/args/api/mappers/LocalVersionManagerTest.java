package ru.taskmanager.tests.args.api.mappers;

import org.junit.Test;
import ru.taskmanager.api.Version;
import ru.taskmanager.api.mappers.LocalVersionManager;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class LocalVersionManagerTest {
    @Test
    public void select() throws IOException {
        LocalVersionManager manager = new LocalVersionManager();
        List<Version> list = manager.select();

        assertTrue(list.size() > 0);
    }
}
