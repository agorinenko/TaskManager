package ru.taskmanager.sql;

import ru.taskmanager.errors.CommandException;
import ru.taskmanager.utils.SettingsUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static volatile ConnectionManager instance;

    private static void init() throws CommandException {
        String driver = SettingsUtils.getSettingsValue("commands.imp.db.driver");
        String driverName = SettingsUtils.getSettingsValue("commands.imp.db.driver.name");

        URL u;
        try {
            u = new URL(driver);
        } catch (MalformedURLException e) {
            throw new CommandException("Invalid URL " + driver);
        }
        URLClassLoader ucl = new URLClassLoader(new URL[] { u });
        Driver d;
        try {
            d = (Driver)Class.forName(driverName, true, ucl).newInstance();

        } catch (ClassNotFoundException e) {
            throw new CommandException("Driver " + driver + " not found");
        } catch (InstantiationException e) {
            throw new CommandException("Driver " + driver + " cannot be instantiated");
        } catch (IllegalAccessException e) {
            throw new CommandException("Illegal access to driver " + driver);
        }
        try {
            DriverManager.registerDriver(new ActiveDriver(d));
        } catch (SQLException e) {
            throw new CommandException("Register driver exception " + driver);
        }
    }

    public static ConnectionManager getInstance() {
        ConnectionManager localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionManager();
                    init();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() throws SQLException {
        String url = SettingsUtils.getSettingsValue("commands.imp.db.url");

        return DriverManager.getConnection(url);
    }
}
