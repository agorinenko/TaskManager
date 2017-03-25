package ru.taskmanager.sql;

import ru.taskmanager.errors.CommandException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataUtils {

    public static void createConnection(SqlAction action) throws CommandException, SQLException {

        if(null == action){
            throw new CommandException("Sql action is null");
        }

        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            action.execute(conn);
        } finally {
            if (null != conn) {
                conn.close();
            }
        }
    }

    public static void executeStatements(Connection conn, List<String> statements) throws SQLException {
        Statement stmt = conn.createStatement();
        try {
            for (String sql : statements) {
                try {
                    stmt.execute(sql);
                }catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            }
        } finally {
            if (null != stmt) {
                stmt.close();
            }
        }
    }
    public static void executeStatementsAsTransaction(Connection conn, List<String> statements) throws SQLException {
        conn.setAutoCommit(false);
        try {
            executeStatements(conn, statements);
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            if (null != conn) {
                conn.setAutoCommit(true);
            }
        }
    }
}
