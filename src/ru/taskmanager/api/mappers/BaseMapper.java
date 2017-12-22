package ru.taskmanager.api.mappers;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.CommandException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMapper<T> {
    private List<T> result;

    protected abstract T createInstanceOfRow();

    protected List<T> select(ResultSet rs) throws SQLException {
        List<T> rows  = new ArrayList<>();

        while (rs.next()) {
            T row = createInstanceOfRow();
            mapFields(row, rs);

            rows.add(row);
        }

        return rows;
    }

    public List<T> getResult() {
        return result;
    }

    public void initResult(ResultSet rs) throws SQLException {
        this.result = select(rs);
    }

    public abstract int insert(List<KeyValueParam> params, T row) throws CommandException;

    protected abstract void mapFields(T row, ResultSet rs) throws SQLException;
}
