package ru.taskmanager.commands.imp.db;

import ru.taskmanager.api.mappers.BaseMapper;
import ru.taskmanager.args.params.BooleanParam;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.config.Settings;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StatementUtils;
import ru.taskmanager.utils.StringUtils;

import java.util.List;

public class Init extends SafetyCommand {
    @Override
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        BooleanParam onlySchemaParam = (BooleanParam) ListUtils.getKeyValueParam(params, "onlyschema");
        Boolean onlySchema = null != onlySchemaParam ? onlySchemaParam.getBooleanValue() : false;

        if(!onlySchema) {
            List<String> createDbStatements = StatementUtils.getDbFolderStatements("create_db.sql");
            DataUtils.createConnectionInCommandContext(conn -> DataUtils.executeStatements(conn, createDbStatements), true);
        }

        List<String> createSchemaStatements = StatementUtils.getDbFolderStatements("create_schema.sql");
        DataUtils.createConnectionInCommandContext(conn -> DataUtils.executeStatementsAsTransaction(conn, createSchemaStatements));

        return createSingleSuccessResult("The create db '" + SettingsUtils.getSettingsValue("db.name") + "' operation was successful");
    }
}
