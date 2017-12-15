package ru.taskmanager.commands.imp.db;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StatementUtils;
import ru.taskmanager.utils.StringUtils;

import java.util.List;

public class Remove  extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";

        List<String> createDbStatements = StatementUtils.getDbFolderStatements("drop_db.sql");
        DataUtils.createConnectionInCommandContext(conn -> DataUtils.executeStatements(conn, createDbStatements), true);

        resultMessage = "The drop db '" + SettingsUtils.getSettingsValue("db.name") + "' operation was successful";

        result.setMessage(resultMessage + System.lineSeparator());
        return result;
    }
}
