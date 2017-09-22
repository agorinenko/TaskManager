package ru.taskmanager.commands.imp.oro;

import com.orodruin.server.InetServer;
import com.orodruin.server.MasterServer;
import com.orodruin.server.exceptions.ServerException;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;

import java.util.List;

/**
 * Created by agorinenko on 21.09.2017.
 */
public class StopServer extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {
        SuccessResult result = new SuccessResult();
        return result;
    }
}
