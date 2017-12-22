package ru.taskmanager.api.mappers;

import ru.taskmanager.api.LocalVersion;
import ru.taskmanager.api.Row;
import ru.taskmanager.api.Version;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.StatementUtils;
import ru.taskmanager.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class VersionMapper extends BaseMapper<Version> {

    @Override
    protected Version createInstanceOfRow() {
        return new Version();
    }

    @Override
    public int insert(List<KeyValueParam> params, Version row) throws CommandException {

        final int[] resultId = {-1};

        LocalVersion version = (LocalVersion) row;
        List<String> insertVersionStatements = StatementUtils.getDbFolderStatements(params, "insert_version.sql");
        String insertVersionStatement = StatementUtils.getSingleStatement(insertVersionStatements);
        if(!StringUtils.isNullOrEmpty(insertVersionStatement)) {
            List<String> insertStatements = version.getStatements(params);
            if (insertStatements.size() > 0) {
                List<BaseMapper> sqlResult = DataUtils.createConnectionInCommandContext(params, conn -> {
                    Object[] ps = {
                            version.getVersionTimestampString(),
                            version.getCreatedBy(),
                            version.getName(),
                            null == version.getDescription() ? "" : version.getDescription()
                    };

                    DataUtils.executeStatementsAsTransaction(conn, insertStatements);

                    return DataUtils.executeStatement(conn, insertVersionStatement, ps, () -> new InsertVersionMapper());
                });

                if(sqlResult.size() > 0) {
                    InsertVersionMapper result = (InsertVersionMapper) sqlResult.get(0);
                    List<Row> rows = result.getResult();
                    if(rows.size() > 0) {
                        Row newVersion = rows.get(0);
                        resultId[0] = newVersion.getId();
                    }
                }
            } else {
                resultId[0] = 0;
            }
        }

        return resultId[0];
    }

    @Override
    protected void mapFields(Version version, ResultSet rs) throws SQLException {
        version.setId(rs.getInt("id"));
        try {
            version.setVersionTimestamp(StringUtils.sdf.parse(rs.getString("version")));
        } catch (ParseException e) {
            version.setVersionTimestamp(null);
        }
        version.setName(rs.getString("name"));
        version.setDescription(rs.getString("description"));
        version.setCreatedBy(rs.getString("created_by"));
        version.setCreatedAt(rs.getDate("created_at"));
    }
}
