package ru.taskmanager.api.mappers;

import ru.taskmanager.api.LocalVersion;
import ru.taskmanager.api.Version;
import ru.taskmanager.utils.SettingsUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    public static LocalVersion convertToLocalVersion(Version version){
        String v = version.getVersion();

        return new LocalVersion(v);
    }

    public static List<LocalVersion> convertToLocalVersions(List<Version> versions){
        List<LocalVersion> list = new ArrayList<>(versions.size());

        versions.forEach(i -> list.add(convertToLocalVersion(i)));

        return list;
    }

    public static List<LocalVersion> merge(List<LocalVersion> first, List<LocalVersion> second){
        List<LocalVersion> sum = new ArrayList<>(first);

        second.forEach(i -> {
            if(!versionExist(first, i)){
                sum.add(i);
            }
        });

        return sum;
    }

    public static boolean versionExist(List<LocalVersion> first, LocalVersion version){
        return first.stream().anyMatch(k -> {
            try {
                return k.getVersionTimestamp().toString().equalsIgnoreCase(version.getVersionTimestamp().toString());
            } catch (ParseException e) {
                return false;
            }
        });
    }
}
