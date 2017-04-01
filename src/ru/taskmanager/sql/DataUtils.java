package ru.taskmanager.sql;

import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.ConnectionManagerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataUtils {

    public static void createConnection(SqlAction action, boolean isGlobal) throws ConnectionManagerException, SQLException {
        if(null == action){
            throw new ConnectionManagerException("Sql action is null");
        }

        Connection conn = isGlobal ? ConnectionManager.getInstance().getGlobalConnection(): ConnectionManager.getInstance().getConnection();
        try {
            action.execute(conn);
        } finally {
            if (null != conn) {
                conn.close();
            }
        }
    }
    public static void createConnection(SqlAction action) throws ConnectionManagerException, SQLException {
        createConnection(action, false);
    }

    public static void executeStatements(Connection conn, List<String> statements, HashMap<String, Object> params) throws SQLException {
        for (String sql : statements) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            try{
                int i = 1;
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    //String key = entry.getKey();
                    Object value = entry.getValue();
                    if(value instanceof Integer){
                        stmt.setInt(i, (int)entry.getValue());
                    } else if(value instanceof String){
                        stmt.setString(i, (String) entry.getValue());
                    }

                    i++;
                }
                stmt.execute();
            }finally {
                if (null != stmt) {
                    stmt.close();
                }
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
