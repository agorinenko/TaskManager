package ru.taskmanager.commands;

import ru.taskmanager.args.params.KeyValueParam;

import java.util.List;

public interface Command {
    /**
     * @param params Параметры команды
     * @return Результат выполнения команды
     */
    List<CommandResult> execute(List<KeyValueParam> params);
}
