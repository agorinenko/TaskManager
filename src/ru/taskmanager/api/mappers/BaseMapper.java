package ru.taskmanager.api.mappers;

import ru.taskmanager.api.Row;
import ru.taskmanager.errors.ConnectionManagerException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.StatementUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMapper {

    protected abstract Row rowObjectInit();

    public List<Row> select(ResultSet rs) throws SQLException {
        List<Row> rows  = new ArrayList<>();

        while (rs.next()) {
            Row row = rowObjectInit();
            row.init(rs);

            rows.add(row);
        }

        return rows;
    }

    public List<Row> select(String statementFile) throws SQLException, ConnectionManagerException {
        List<String> selectVersionsStatements = StatementUtils.getStatements(statementFile);

        DataUtils.createConnection(conn -> {
            List<ResultSet> sqlResult = DataUtils.executeStatementsAsTransaction(conn, selectVersionsStatements);
            ResultSet rs = sqlResult.get(0);

            List<Row> result = select(rs);
        });

        return result;
    }
}
