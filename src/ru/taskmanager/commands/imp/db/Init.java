package ru.taskmanager.commands.imp.db;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Init extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";

        String url = SettingsUtils.getSettingsValue("commands.imp.db.url");
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url);
            String file = StringUtils.trimEnd(System.getProperty("user.dir"), "//") + "/scripts/pg/create_db.sql";
            Path filePath= Paths.get(file).normalize();

            Reader reader = Files.newBufferedReader(filePath);
            LineNumberReader lineReader = new LineNumberReader(reader);
            String line;
            while ((line = lineReader.readLine()) != null) {
                System.out.print(line);
            }


        } catch (SQLException e) {
            throw new CommandException(String.format("URL:%1$s; MESSAGE:%2$s; CODE:%3$s;", url, e.getMessage(), e.getErrorCode()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.setMessage(resultMessage);
        return result;
    }
}
