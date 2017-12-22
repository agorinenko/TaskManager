package ru.taskmanager.api.mappers;

import ru.taskmanager.api.LocalVersion;
import ru.taskmanager.api.Version;
import ru.taskmanager.api.VersionComparator;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LocalVersionManager {
    private String baseDir;

    public LocalVersionManager(List<KeyValueParam> params){
        this.baseDir = SettingsUtils.getOutScriptDir(params);
    }

    public List<Version> select() throws IOException {
            return select("");
    }

    public List<Version> select(String extension) throws IOException {
        List<Version> rows  = new ArrayList<>();

        Path path = Paths.get(baseDir);
        path = path.normalize();
        if (Files.exists(path)) {
            try(Stream<Path> paths = createPaths(path, extension)) {

                paths.forEach(filePath -> {
                    Version local = new LocalVersion(filePath);
                    if(null != local.getVersionTimestamp()) {
                        rows.add(local);
                    }
                });
            }
        } else {
            throw new IOException("Path '" + path + "' not found");
        }
        return rows;
    }

    private Stream<Path> createPaths(Path path, String extension) throws IOException {

        if(!StringUtils.isNullOrEmpty(extension)){
            return Files.walk(path).filter(Files::isRegularFile).filter(foundPath -> foundPath.toString().endsWith(extension));
        }else {
            return Files.walk(path).filter(Files::isRegularFile);
        }
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

    public Path createFile(String filePath) throws CommandException {
        if(!StringUtils.isNullOrEmpty(this.baseDir)){
            filePath = String.format("%1$s\\%2$s", StringUtils.trimEnd(this.baseDir, "\\\\"), StringUtils.trimStart(filePath, "\\\\"));
        }

        Path path = Paths.get(filePath);
        path = path.normalize();

        Path parent  = path.getParent();
        try {
            if(null != parent && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            throw new CommandException(String.format("Create new dir '%1$s' IOException", parent));
        }

        try {
            return Files.createFile(path);
        } catch (IOException e) {
            throw new CommandException(String.format("Create new file '%1$s' IOException", path));
        }
    }
}
