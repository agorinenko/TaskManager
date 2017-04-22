package ru.taskmanager.commands.imp.db;

import ru.taskmanager.api.mappers.BaseMapper;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.StatementUtils;

import java.sql.ResultSet;
import java.util.List;

public class Init extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";

            List<String> createDbStatements = StatementUtils.getStatements("create_db.sql");
            List<String> createSchemaStatements = StatementUtils.getStatements("create_schema.sql");

            DataUtils.createConnectionInCommandContext(conn -> {
                DataUtils.executeStatements(conn, createDbStatements);
            }, true);

            DataUtils.createConnectionInCommandContext(conn -> {
                DataUtils.executeStatementsAsTransaction(conn, createSchemaStatements);
            });

            resultMessage = "The operation was successful";

        result.setMessage(resultMessage);
        return result;
    }
}
