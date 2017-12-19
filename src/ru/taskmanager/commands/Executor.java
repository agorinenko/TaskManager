package ru.taskmanager.commands;

import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.args.params.CommandParam;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.errors.RequiredParamException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.output.Printer;

import java.util.ArrayList;
import java.util.List;

public final class Executor {
    private ParamsManager paramsManager;

    public Executor(ParamsManager paramsManager) {
        this.paramsManager = paramsManager;
    }

    public List<CommandResult> execute() throws RequiredParamException, ClassNotFoundException, InstantiationException, StringIsEmptyException, IllegalAccessException, ConfigurationException {
        List<CommandResult> result  = new ArrayList<>();
        List<CommandParam> commands = paramsManager.getCommandParams();
        List<KeyValueParam> params = paramsManager.getKeyValueParams();

        for (CommandParam commandParam : commands) {
            List<CommandResult> commandResult = executeCommand(commandParam, params);
            result.addAll(commandResult);
        }

        return result;
    }

    public void executeAndPrint(Printer printer) throws StringIsEmptyException, InstantiationException, ClassNotFoundException, IllegalAccessException, ConfigurationException, RequiredParamException {
        List<CommandResult> result = execute();
        printer.print(result);
    }

    private List<CommandResult> executeCommand(CommandParam commandParam, List<KeyValueParam> params) throws ClassNotFoundException, InstantiationException, StringIsEmptyException, IllegalAccessException, ConfigurationException {
        CommandMapper mapper = new CommandMapper(commandParam);
        Command command = mapper.initCommandObject();

        return command.execute(params);
    }
}
