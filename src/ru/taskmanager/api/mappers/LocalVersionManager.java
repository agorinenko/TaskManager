package ru.taskmanager.api.mappers;

import ru.taskmanager.api.LocalVersion;
import ru.taskmanager.api.Version;
import ru.taskmanager.api.VersionComparator;
import ru.taskmanager.utils.SettingsUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
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
                    LocalVersion local = new LocalVersion(fileName);
                    try {
                        local.getVersionTimestamp();
                        rows.add(local);
                    } catch (ParseException e) {
                    }
                });
            }
        }
        return rows;
    }

    public static List<LocalVersion> convertToLocalVersions(List<Version> versions){
        List<LocalVersion> list = new ArrayList<>(versions.size());
        versions.forEach(i -> list.add(new LocalVersion(i)));
        return list;
    }

    public static List<LocalVersion> merge(List<LocalVersion> first, List<LocalVersion> second){
        List<LocalVersion> sum = new ArrayList<>();

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

    public static boolean versionExist(List<LocalVersion> list, LocalVersion version){
        return list.stream().anyMatch(k -> {
            VersionComparator comparator = new VersionComparator();
            return comparator.compare(k, version) == 0;
        });
    }
}
