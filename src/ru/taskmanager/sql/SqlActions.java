package ru.taskmanager.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface SqlActions {
    void execute(Connection conn) throws SQLException;
}
