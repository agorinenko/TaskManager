package ru.taskmanager.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class StatementUtils {

    public static List<String> getStatements(String fileName) {
        String separator = SettingsUtils.getSettingsValue("db.separator.default");

        String baseScriptDir = SettingsUtils.getBaseScriptDir();

        fileName = StringUtils.trimStart(fileName, "//");

        String file =  baseScriptDir + "/" + fileName;

        List<String> statements;
        try {
            StatementQueueBuilder builder = new StatementQueueBuilder(file, separator);
            builder.build();
            statements = builder.getStatements();
        } catch (FileNotFoundException e) {
            statements = new ArrayList<>();
        }


        return statements;
    }
}
