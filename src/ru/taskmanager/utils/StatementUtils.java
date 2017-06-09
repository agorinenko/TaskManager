package ru.taskmanager.utils;

import java.io.FileNotFoundException;
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
        String baseScriptDir = SettingsUtils.getBaseScriptDir();
        return getStatements(baseScriptDir, fileName);
    }

    public static List<String> getOutFolderStatements(String fileName) {
        String baseScriptDir = SettingsUtils.getOutScriptDir();
        return getStatements(baseScriptDir, fileName);
    }

    public static List<String> getStatements(String baseScriptDir, String fileName) {
        fileName = StringUtils.trimStart(fileName, "//");
        String fullFilePath =  baseScriptDir + "/" + fileName;

        return getStatementsByFullFilePath(fullFilePath);
    }

    public static List<String> getStatementsByFullFilePath(String fullFilePath) {
        List<String> statements;
        String separator = SettingsUtils.getSettingsValue("db.separator.default");
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
