package ru.taskmanager.commands.imp.db;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.StatementUtils;

import java.sql.ResultSet;
import java.util.List;

public class Push extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";

        List<String> selectVersionsStatements = StatementUtils.getStatements("select_versions.sql");

        DataUtils.createConnectionInCommandContext(conn -> {
            List<ResultSet> sqlResult = DataUtils.executeStatementsAsTransaction(conn, selectVersionsStatements);
        });

        result.setMessage(resultMessage);
        return result;
    }
}
