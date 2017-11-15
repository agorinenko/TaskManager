package ru.taskmanager.sql;

import ru.taskmanager.errors.CommandException;
import ru.taskmanager.errors.ConnectionManagerException;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static volatile ConnectionManager instance;

    private String url;
    private String urlWithDb;
    private String user;
    private String pwd;
    private String dbName;

    private String driver;
    private String driverName;

    private ConnectionManager() throws ConnectionManagerException {
        driver = (String) SettingsUtils.getSettingsOrDefaultValue("db.driver");
        driver = StringUtils.replaceAllSpecialConstants(driver);
        driverName = (String) SettingsUtils.getSettingsValue("db.driver.name");

        url = (String) SettingsUtils.getSettingsValue("db.url");
        user = (String) SettingsUtils.getSettingsValue("db.user");
        pwd = (String) SettingsUtils.getSettingsValue("db.pwd");

        dbName = (String) SettingsUtils.getSettingsValue("db.name");

        urlWithDb = formatUrl();

        URL u;
        try {
            u = new URL(driver);
        } catch (MalformedURLException e) {
            throw new ConnectionManagerException("Invalid URL " + driver);
        }
        URLClassLoader ucl = new URLClassLoader(new URL[] { u });
        Driver d;
        try {
            d = (Driver)Class.forName(driverName, true, ucl).newInstance();
        } catch (ClassNotFoundException e) {
            throw new ConnectionManagerException("Driver " + driver + " not found");
        } catch (InstantiationException e) {
            throw new ConnectionManagerException("(Instantiation driver " + driver + " exception:" + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ConnectionManagerException("Illegal access driver " + driver + " exception:" + e.getMessage());
        }
        try {
            DriverManager.registerDriver(new ActiveDriver(d));
        } catch (SQLException e) {
            throw new ConnectionManagerException("Sql driver " + driver + " exception:" + e.getMessage());
        }
    }

    public static ConnectionManager getInstance() throws ConnectionManagerException {
        ConnectionManager localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionManager();
                }
            }
        }

        return localInstance;
    }

    public Connection getGlobalConnection() throws SQLException {
       Connection conn = DriverManager.getConnection(url, user, pwd);
        return conn;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(urlWithDb, user, pwd);
        return conn;
    }

    private String formatUrl() throws ConnectionManagerException {
        if(driverName.equalsIgnoreCase("org.postgresql.Driver")){
            String _url = StringUtils.trimEnd(url, "//");
            return String.format("%1$s/%2$s", _url, dbName);
        } else if(driverName.equalsIgnoreCase("com.microsoft.sqlserver.jdbc.SQLServerDriver")){
            String _url = StringUtils.trimEnd(url, ";");
            return String.format("%1$s;databaseName=%2$s;", _url, dbName);
        }

        throw new ConnectionManagerException(String.format("Driver %1$s doesn't support", driverName));
    }
}
