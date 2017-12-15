package utils;

import org.junit.Assert;
import org.junit.Test;
import ru.taskmanager.config.EnvironmentVariables;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.TreeCopier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TreeCopierTest {

    @Test
    public void initTreeCopierTest() throws ConfigurationException, IOException {
        TreeCopier copier = new TreeCopier(Paths.get("C:\\test"), Paths.get("\\\\172.26.72.233\\install\\release01.01"));
        copier.copy();
    }
}
