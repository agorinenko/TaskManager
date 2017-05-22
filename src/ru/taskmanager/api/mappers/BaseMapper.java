package ru.taskmanager.api.mappers;

import ru.taskmanager.api.Row;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMapper<T extends Row> {
    private List<T> result;

    protected abstract T createInstanceOfRow();

    protected List<T> select(ResultSet rs) throws SQLException {
        List<T> rows  = new ArrayList<>();

        while (rs.next()) {
            T row = createInstanceOfRow();
            row.init(rs);

            rows.add(row);
        }

        return rows;
    }

    public List<T> getResult() {
        return result;
    }

    public void initResilt(ResultSet rs) throws SQLException {
        this.result = select(rs);
    }

    public abstract int insert(T row);
}
