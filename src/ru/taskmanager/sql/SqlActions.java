package ru.taskmanager.sql;

import ru.taskmanager.api.mappers.BaseMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface SqlActions {
    List<BaseMapper> execute(Connection conn) throws SQLException;
}
