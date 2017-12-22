package ru.taskmanager.api;

import ru.taskmanager.api.mappers.BaseMapper;
import ru.taskmanager.api.mappers.InsertVersionMapper;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.DataUtils;
import ru.taskmanager.utils.StatementUtils;
import ru.taskmanager.utils.StringUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Row {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public int delete(List<KeyValueParam> params) throws CommandException {
        throw new NotImplementedException();
    }
}
