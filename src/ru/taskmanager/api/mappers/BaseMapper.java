package ru.taskmanager.api.mappers;

import ru.taskmanager.api.Row;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseMapper<T extends Row> {

    //private final Constructor<? extends T> ctor;

    private List<T> result;

//    protected BaseMapper(Class<? extends T> impl) throws NoSuchMethodException {
//        this.ctor =  impl.getConstructor();;
//    }

    protected abstract T createInstanceOfRow();

    protected List<T> select(ResultSet rs) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException {
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

    public void initResilt(ResultSet rs) throws SQLException, IllegalAccessException, InstantiationException, InvocationTargetException {
        this.result = select(rs);
    }

    public abstract boolean insert(T row);

    public abstract boolean update(T row);
}
