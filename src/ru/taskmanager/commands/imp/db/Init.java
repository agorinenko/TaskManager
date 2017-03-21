package ru.taskmanager.commands.imp.db;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.ActiveDriver;
import ru.taskmanager.sql.ConnectionManager;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StatementQueueBuilder;
import ru.taskmanager.utils.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.List;

public class Init extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";


        String separator = SettingsUtils.getSettingsValue("commands.imp.db.separator.default");

        try {
            String file = StringUtils.trimEnd(System.getProperty("user.dir"), "//") + "/settings/scripts/pg/create_db.sql";
            StatementQueueBuilder builder = new StatementQueueBuilder(file, separator);
            builder.build();
            List<String> statements = builder.getStatements();

            Connection conn = ConnectionManager.getInstance().getConnection();
            try {
                conn.setAutoCommit(false);
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

                        conn.commit();
                    } finally {
                        if (null != stmt) {
                            stmt.close();
                        }
                    }
                } finally {
                    if (null != conn) {
                        conn.setAutoCommit(true);
                        conn.close();
                    }
                }
        } catch (SQLException e) {
            throw new CommandException(String.format("MESSAGE:%2$s; CODE:%3$s;", e.getMessage(), e.getErrorCode()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();        }

        result.setMessage(resultMessage);
        return result;
    }
}
