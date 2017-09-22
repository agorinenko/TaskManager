package ru.taskmanager.tests.args.commands.imp;

import org.junit.Test;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.RequiredParamException;
import ru.taskmanager.errors.StringIsEmptyException;

import java.util.List;

/**
 * Created by agorinenko on 21.09.2017.
 */
public class OroCommand {
    @Test
    public void startMaster() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException {
        ParamsManager manager = new ParamsManager(new String[]{ "oro", "o:start", "t:master" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
    }
}
