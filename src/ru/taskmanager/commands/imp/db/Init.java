package ru.taskmanager.commands.imp.db;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.ConnectionManagerException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.StatementUtils;

import java.io.*;
import java.sql.*;
import java.util.List;

public class Init extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";

        try {
            List<String> createDbStatements = StatementUtils.getStatements("create_db.sql");
            List<String> createSchemaStatements = StatementUtils.getStatements("create_schema.sql");

            DataUtils.createConnection(conn -> {
                DataUtils.executeStatements(conn, createDbStatements);
            }, true);

            DataUtils.createConnection(conn -> {
                DataUtils.executeStatementsAsTransaction(conn, createSchemaStatements);
            });
            resultMessage = "The operation was successful";
        } catch (SQLException e) {
            throw new CommandException(String.format("MESSAGE:%1$s; CODE:%2$s;", e.getMessage(), e.getErrorCode()));
        } catch (IOException e) {
            throw new CommandException(String.format("%1$s", e.getMessage()));
        } catch (ConnectionManagerException e) {
            throw new CommandException(String.format("%1$s", e.getMessage()));
        }

        result.setMessage(resultMessage);
        return result;
    }
}
