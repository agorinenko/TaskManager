package ru.taskmanager.commands.imp.db;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.ActiveDriver;
import ru.taskmanager.sql.ConnectionManager;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StatementQueueBuilder;
import ru.taskmanager.utils.StringUtils;

import java.io.*;
import java.sql.*;
import java.util.List;

public class Init extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";

        String separator = SettingsUtils.getSettingsValue("commands.imp.db.separator.default");

        try {
            String file = StringUtils.trimEnd(System.getProperty("user.dir"), "//") + "/settings/scripts/pg/create_db.sql";
            StatementQueueBuilder builder = new StatementQueueBuilder(file, separator);
            builder.build();
            List<String> statements = builder.getStatements();

            DataUtils.createConnection(conn -> {
                DataUtils.executeStatements(conn, statements);
            });

        } catch (SQLException e) {
            throw new CommandException(String.format("MESSAGE:%1$s; CODE:%2$s;", e.getMessage(), e.getErrorCode()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();        }

        result.setMessage(resultMessage);
        return result;
    }
}
