package ru.taskmanager.commands.imp.db;

import ru.taskmanager.api.VersionsRepository;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.utils.ListUtils;
import ru.taskmanager.utils.StringUtils;

import java.util.List;

public abstract class BaseDbCommand extends SafetyCommand {

    protected VersionsRepository initVersionsRepository(List<KeyValueParam> params){
        VersionsRepository versionsRepository = new VersionsRepository();

        return versionsRepository;
    }
}
