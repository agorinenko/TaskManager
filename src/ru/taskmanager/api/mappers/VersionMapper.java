package ru.taskmanager.api.mappers;

import ru.taskmanager.api.LocalVersion;
import ru.taskmanager.api.Row;
import ru.taskmanager.api.Version;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.StatementUtils;
import ru.taskmanager.utils.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class VersionMapper extends BaseMapper {

    @Override
    protected Object createInstanceOfRow() {
        return new Version();
    }

    @Override
    public int insert(Object row) throws CommandException {

        final int[] resultId = {-1};

        LocalVersion version = (LocalVersion) row;
        List<String> insertVersionStatements = StatementUtils.getDbFolderStatements("insert_version.sql");
        String insertVersionStatement = StatementUtils.getSingleStatement(insertVersionStatements);
        if(!StringUtils.isNullOrEmpty(insertVersionStatement)) {
            List<String> insertStatements = version.getStatements();
            if (insertStatements.size() > 0) {
                List<BaseMapper> sqlResult = DataUtils.createConnectionInCommandContext(conn -> {
                    Object[] params = {
                            version.getVersionTimestampString(),
                            version.getCreatedBy(),
                            version.getName(),
                            null == version.getDescription() ? "" : version.getDescription()
                    };

                    DataUtils.executeStatementsAsTransaction(conn, insertStatements);

                    return DataUtils.executeStatement(conn, insertVersionStatement, params, () -> new InsertVersionMapper());
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
    protected void mapFields(Object row, ResultSet rs) throws SQLException {
        Version version = (Version) row;
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
