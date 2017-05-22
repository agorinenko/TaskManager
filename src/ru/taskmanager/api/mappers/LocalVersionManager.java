package ru.taskmanager.api.mappers;

import ru.taskmanager.api.LocalVersion;
import ru.taskmanager.utils.SettingsUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LocalVersionManager {
    private String baseDir;

    public LocalVersionManager(){
        baseDir = SettingsUtils.getSettingsValue("out");
    }

    public List<LocalVersion> select() throws IOException {
        List<LocalVersion> rows  = new ArrayList<>();

        Path path = Paths.get(baseDir);
        path = path.normalize();
        if (Files.exists(path)) {
            try(Stream<Path> paths = Files.walk(path).filter(Files::isRegularFile)) {
                paths.forEach(filePath -> {
                    String fileName = filePath.getFileName().toString();
                    rows.add(new LocalVersion(fileName));
                });
            }
        }


        return rows;
    }
}
