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
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        List<String> createDbStatements = StatementUtils.getDbFolderStatements(params, "drop_db.sql");
        DataUtils.createConnectionInCommandContext(params, conn -> DataUtils.executeStatements(conn, createDbStatements), true);

        return createSingleSuccessResult("The drop db '" + SettingsUtils.getSettingsOrParamValue(params, "db.name") + "' operation was successful");
    }
}
