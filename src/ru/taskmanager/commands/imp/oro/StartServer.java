package ru.taskmanager.commands.imp.oro;

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
public class StartServer extends SafetyCommand {
    @Override
    public List<CommandResult> safetyExecute(List<KeyValueParam> params) throws CommandException {
        try {
            MasterServer.start(8888);
            return createSingleSuccessResult("");
        } catch (ServerException e) {
            throw new CommandException(e.toString());
        }
    }
}
