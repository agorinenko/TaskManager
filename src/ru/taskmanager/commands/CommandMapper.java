package ru.taskmanager.commands;

import ru.taskmanager.args.params.CommandParam;
import ru.taskmanager.config.Commands;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.errors.StringIsEmptyException;

import java.util.Map;

public final class CommandMapper {
    private CommandParam param;

    public CommandMapper(CommandParam param){
        this.param = param;
    }

    public Command initCommandObject() throws ClassNotFoundException, StringIsEmptyException, IllegalAccessException, InstantiationException, ConfigurationException {
        String key = param.getKey();
        Map<String, String> map = Commands.getInstance().getEntityByKey(key);
        String className = map.get("class");

        Class commandClass = Class.forName(className);
        Object commandInstance = commandClass.newInstance();
        Command c = (Command)commandInstance;

        return c;
    }
}
