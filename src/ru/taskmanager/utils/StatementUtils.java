package ru.taskmanager.utils;

import ru.taskmanager.args.params.KeyValueParam;

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

    public static List<String> getDbFolderStatements(List<KeyValueParam> params, String fileName) {
        String scriptPath = SettingsUtils.getScriptPath(params, fileName);
        return getStatementsByFullFilePath(params, scriptPath);
    }

    public static List<String> getStatementsByFullFilePath(List<KeyValueParam> params, String fullFilePath) {
        List<String> statements;
        String separator = (String) SettingsUtils.getSettingsOrParamValue(params, "db.separator.default");
        try {
            StatementQueueBuilder builder = new StatementQueueBuilder(fullFilePath, separator);
            builder.build(params);
            statements = builder.getStatements();
        } catch (FileNotFoundException e) {
            statements = new ArrayList<>();
        }

        return statements;
    }
}
