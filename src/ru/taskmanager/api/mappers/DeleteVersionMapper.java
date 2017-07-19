package ru.taskmanager.api.mappers;
import ru.taskmanager.api.Row;
import ru.taskmanager.errors.CommandException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteVersionMapper extends BaseMapper {

    @Override
    protected Object createInstanceOfRow() {
        return new Row();
    }

    @Override
    public int insert(Object row) throws CommandException {
        throw new NotImplementedException();
    }

    @Override
    protected void mapFields(Object row, ResultSet rs) throws SQLException {
        Row version = (Row) row;
        version.setId(rs.getInt("res"));
    }
}
