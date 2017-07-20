package ru.taskmanager.api;

import ru.taskmanager.utils.StatementUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LocalVersion extends Version {
    private Path localPath;

    public LocalVersion(Path filePath) {
        super(filePath.getFileName().toString());
        this.localPath = filePath;
    }

    public Path getLocalPath(){
        return this.localPath;
    }

    public List<String> getStatements() {
        return StatementUtils.getStatementsByFullFilePath(getLocalPath().toString());
    }
}
