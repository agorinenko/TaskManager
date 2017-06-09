package ru.taskmanager.api.mappers;

import ru.taskmanager.api.Version;
import ru.taskmanager.api.VersionComparator;
import ru.taskmanager.utils.SettingsUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LocalVersionManager {
    private String baseDir;

    public LocalVersionManager(){
        baseDir = SettingsUtils.getSettingsValue("out");
    }

    public List<Version> select() throws IOException {
        List<Version> rows  = new ArrayList<>();

        Path path = Paths.get(baseDir);
        path = path.normalize();
        if (Files.exists(path)) {
            try(Stream<Path> paths = Files.walk(path).filter(Files::isRegularFile)) {
                paths.forEach(filePath -> {
                    String fileName = filePath.getFileName().toString();
                    Version local = new Version(fileName);
                    if(null != local.getVersionTimestamp()) {
                        rows.add(local);
                    }
                });
            }
        }
        return rows;
    }

    public static List<Version> merge(List<Version> first, List<Version> second){
        List<Version> sum = new ArrayList<>();

        first.forEach(i -> {
            if(!versionExist(sum, i)){
                sum.add(i);
            }
        });

        second.forEach(i -> {
            if(!versionExist(sum, i)){
                sum.add(i);
            }
        });

        return sum;
    }

    public static boolean versionExist(List<Version> list, Version version){
        return list.stream().anyMatch(k -> {
            VersionComparator comparator = new VersionComparator();
            return comparator.compare(k, version) == 0;
        });
    }
}
