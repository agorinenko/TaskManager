package ru.taskmanager.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface SqlAction {
    void execute(Connection conn) throws SQLException;
}
