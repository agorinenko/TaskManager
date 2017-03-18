package ru.taskmanager.commands.imp.db;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.sql.ActiveDriver;
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

        String url = SettingsUtils.getSettingsValue("commands.imp.db.url");
        String driver = SettingsUtils.getSettingsValue("commands.imp.db.driver");
        String driverName = SettingsUtils.getSettingsValue("commands.imp.db.driver.name");
        String separator = SettingsUtils.getSettingsValue("commands.imp.db.separator.default");

        try {
            URL u = new URL(driver);
            URLClassLoader ucl = new URLClassLoader(new URL[] { u });
            Driver d = (Driver)Class.forName(driverName, true, ucl).newInstance();
            DriverManager.registerDriver(new ActiveDriver(d));

            String file = StringUtils.trimEnd(System.getProperty("user.dir"), "//") + "/scripts/pg/create_db.sql";
            StatementQueueBuilder builder = new StatementQueueBuilder(file, separator);
            builder.build();
            List<String> statements = builder.getStatements();

            Connection conn = DriverManager.getConnection(url);
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
            throw new CommandException(String.format("URL:%1$s; MESSAGE:%2$s; CODE:%3$s;", url, e.getMessage(), e.getErrorCode()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new CommandException("Driver " + driver + " not found");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        result.setMessage(resultMessage);
        return result;
    }
}
