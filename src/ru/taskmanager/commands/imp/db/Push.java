package ru.taskmanager.commands.imp.db;

import ru.taskmanager.api.Version;
import ru.taskmanager.api.mappers.BaseMapper;
import ru.taskmanager.api.mappers.VersionMapper;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.StatementUtils;

import java.util.List;

public class Push extends SafetyCommand {
    private VersionMapper versionMapper;
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";

        List<String> selectVersionsStatements = StatementUtils.getStatements("select_versions.sql");


        DataUtils.createConnectionInCommandContext(conn -> {

            List<BaseMapper> sqlResult = DataUtils.executeStatementsAsTransaction(conn, selectVersionsStatements, () ->  new VersionMapper());
            versionMapper = (VersionMapper) sqlResult.get(0);
        });

        List<Version> versions = versionMapper.getResult();

//        Version version = new Version();
//        version.setVersion("");
//        version.setCreatedAt();
//        versionMapper.insert(version);

        result.setMessage(resultMessage);
        return result;
    }
}
