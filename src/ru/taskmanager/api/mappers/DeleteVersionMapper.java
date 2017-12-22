package ru.taskmanager.api.mappers;
import ru.taskmanager.api.Row;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.CommandException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DeleteVersionMapper extends BaseMapper<Row> {

    @Override
    protected Row createInstanceOfRow() {
        return new Row();
    }

    @Override
    public int insert(List<KeyValueParam> params, Row row) throws CommandException {
        throw new NotImplementedException();
    }

    @Override
    protected void mapFields(Row version, ResultSet rs) throws SQLException {
        version.setId(rs.getInt("res"));
    }
}
