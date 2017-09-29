package ru.taskmanager.tests.args.commands.imp;

import org.junit.Assert;
import org.junit.Test;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.*;
import ru.taskmanager.utils.CommonUtils;
import ru.taskmanager.utils.SettingsUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by agorinenko on 28.09.2017.
 */
public class PowerShellCommand {
    @Test
    public void executePowerShellScript() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException {
        ParamsManager manager = new ParamsManager(new String[]{ "ps" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void executePowerShellScript1() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException {
        ParamsManager manager = new ParamsManager(new String[]{ "ps", "out:out/dir2" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void executePowerShellScript2() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException {
        ParamsManager manager = new ParamsManager(new String[]{ "ps", "out:out/dir2", "v:20170928161435508" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void executePowerShellScript3() throws IOException, PowerShellException {
        String result = CommonUtils.executePowerShellScript("20170929113452336_file.ps1");

        Assert.assertEquals("1", result);
    }

    @Test
    public void executePowerShellScript4() throws IOException, PowerShellException {
        String result = CommonUtils.executePowerShellScript(SettingsUtils.getBaseSettingsDir()+"/assets/ps/test.ps1");

        Assert.assertEquals("1", result);
    }
}
