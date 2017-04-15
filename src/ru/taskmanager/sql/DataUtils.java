package ru.taskmanager.sql;

import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.ConnectionManagerException;

import java.sql.*;
import java.util.*;

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

    public static void createConnectionInCommandContext(SqlAction action, boolean isGlobal) throws CommandException {
        try {
            createConnection(action, isGlobal);
        } catch (SQLException e) {
            throw new CommandException(String.format("MESSAGE:%1$s; CODE:%2$s;", e.getMessage(), e.getErrorCode()));
        } catch (ConnectionManagerException e) {
            throw new CommandException(String.format("%1$s", e.getMessage()));
        }
    }

    public static void createConnectionInCommandContext(SqlAction action) throws CommandException {
        createConnectionInCommandContext(action, false);
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

    public static List<ResultSet> executeStatements(Connection conn, List<String> statements) throws SQLException {
        Statement stmt = conn.createStatement();
        List<ResultSet> result = new ArrayList<>();
        try {
            for (String sql : statements) {
                stmt.execute(sql);
                ResultSet rs = stmt.getResultSet();
                result.add(rs);
            }
        } finally {
            if (null != stmt) {
                stmt.close();
            }
        }
        return result;
    }
    public static List<ResultSet> executeStatementsAsTransaction(Connection conn, List<String> statements) throws SQLException {
        List<ResultSet> result;
        conn.setAutoCommit(false);
        try {
            result = executeStatements(conn, statements);
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            if (null != conn) {
                conn.setAutoCommit(true);
            }
        }

        return result;
    }
}
