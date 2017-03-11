package ru.taskmanager.commands.imp.db;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;
import ru.taskmanager.utils.SettingsUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Init extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        String resultMessage = "";

        String url = SettingsUtils.getSettingsValue("commands.imp.db.url");
        try {
            Connection conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new CommandException(String.format("URL:%1$s; MESSAGE:%2$s; CODE:%3$s;", url, e.getMessage(), e.getErrorCode()));
        }

        result.setMessage(resultMessage);
        return result;
    }
}
