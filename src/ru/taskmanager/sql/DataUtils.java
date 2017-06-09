package ru.taskmanager.sql;

import ru.taskmanager.api.mappers.BaseMapper;
import ru.taskmanager.api.mappers.VersionMapper;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.ConnectionManagerException;
import ru.taskmanager.utils.Builder;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class DataUtils {

    public static void createConnection(SqlActions action, boolean isGlobal) throws ConnectionManagerException, SQLException {
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
    public static void createConnection(SqlActions action) throws ConnectionManagerException, SQLException {
        createConnection(action, false);
    }

    public static void createConnectionInCommandContext(SqlActions action, boolean isGlobal) throws CommandException {
        try {
            createConnection(action, isGlobal);
        } catch (SQLException e) {
            throw new CommandException(String.format("%1$s; Code:%2$s;", e.getMessage(), e.getErrorCode()));
        } catch (ConnectionManagerException e) {
            throw new CommandException(String.format("%1$s", e.getMessage()));
        }
    }

    public static void createConnectionInCommandContext(SqlActions action) throws CommandException {
        createConnectionInCommandContext(action, false);
    }

    public static<MapperType extends BaseMapper> List<BaseMapper> executeStatement(Connection conn, String statement, Object[] params, Builder<MapperType> builder) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(statement);
        List<BaseMapper> result = new ArrayList<>();
        try{
            int i = 1;
            for (Object value : params) {
                if (value instanceof Integer){
                    stmt.setInt(i, (int)value);
                } else if(value instanceof String){
                    stmt.setString(i, (String)value);
                }

                i++;
            }
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            if(null != builder) {
                MapperType mapper = builder.build();
                mapper.initResult(rs);

                result.add(mapper);
            }
        }finally {
            if (null != stmt) {
                stmt.close();
            }
        }

        return result;
    }


    public static<MapperType extends BaseMapper> List<BaseMapper> executeStatements(Connection conn, List<String> statements, Builder<MapperType> builder) throws SQLException {
        Statement stmt = conn.createStatement();
        List<BaseMapper> result = new ArrayList<>();

        try {
            for (String sql : statements) {
                stmt.execute(sql);
                ResultSet rs = stmt.getResultSet();

                if(null != builder) {
                    MapperType mapper = builder.build();
                    mapper.initResult(rs);

                    result.add(mapper);
                }
            }
        } finally {
            if (null != stmt) {
                stmt.close();
            }
        }

        return result;
    }

    public static void executeStatements(Connection conn, List<String> statements) throws SQLException {
        executeStatements(conn, statements, null);
    }

    public static void executeStatementsAsTransaction(Connection conn, List<String> statements) throws SQLException {
        executeStatementsAsTransaction(conn, statements, null);
    }

    public static<MapperType extends BaseMapper> List<BaseMapper> executeStatementsAsTransaction(Connection conn, List<String> statements, Builder<MapperType> builder) throws SQLException {
        List<BaseMapper> result;
        conn.setAutoCommit(false);
        try {
            result = executeStatements(conn, statements, builder);
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
