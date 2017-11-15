package ru.taskmanager.utils;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StatementUtils {

    public static String getSingleStatement(List<String> statements){
        if(null != statements && statements.size() > 0){
            return statements.get(0);
        }

        return "";
    }

    public static List<String> getDbFolderStatements(String fileName) {
        String scriptPath = SettingsUtils.getScriptPath(fileName);
        return getStatementsByFullFilePath(scriptPath);
    }

    public static List<String> getStatementsByFullFilePath(String fullFilePath) {
        List<String> statements;
        String separator = (String) SettingsUtils.getSettingsValue("db.separator.default");
        try {
            StatementQueueBuilder builder = new StatementQueueBuilder(fullFilePath, separator);
            builder.build();
            statements = builder.getStatements();
        } catch (FileNotFoundException e) {
            statements = new ArrayList<>();
        }

        return statements;
    }
}
