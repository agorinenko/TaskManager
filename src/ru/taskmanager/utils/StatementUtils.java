package ru.taskmanager.utils;

import java.io.IOException;
import java.util.List;

public class StatementUtils {

    public static List<String> getStatements(String fileName) throws IOException {
        String separator = SettingsUtils.getSettingsValue("commands.imp.db.separator.default");

        String baseScriptDir = SettingsUtils.getBaseScriptDir();

        fileName = StringUtils.trimStart(fileName, "//");

        String file =  baseScriptDir + "/" + fileName;

        StatementQueueBuilder builder = new StatementQueueBuilder(file, separator);
        builder.build();
        List<String> statements = builder.getStatements();

        return statements;
    }
}
