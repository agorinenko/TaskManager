package ru.taskmanager.commands;

import ru.taskmanager.args.params.CommandParam;
import ru.taskmanager.errors.StringIsEmptyException;

public final class CommandMapper {
    private CommandParam param;

    public CommandMapper(CommandParam param){
        this.param = param;
    }

    public Command initCommandObject() throws ClassNotFoundException, StringIsEmptyException, IllegalAccessException, InstantiationException {
        Class commandClass = Class.forName("ru.taskmanager.commands.imp."+ param.getKey());
        Object commandInstance = commandClass.newInstance();
        return (Command)commandInstance;
    }
}
