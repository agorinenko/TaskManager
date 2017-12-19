package args.commands.imp;

import org.junit.Assert;
import org.junit.Test;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.ErrorResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.*;
import ru.taskmanager.utils.CommonUtils;
import ru.taskmanager.utils.SettingsUtils;

import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by agorinenko on 28.09.2017.
 */
public class PowerShellCommand {
    @Test
    public void executePowerShellScript() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException, UniqueParamException {
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
    public void executePowerShellScript1() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "ps", "out:out/dir2" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
    }

    @Test
    public void executePowerShellScript2() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "ps", "out:out/dir2", "v:20170929113452336" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
    }

    @Test
    public void executePowerShellScript3() throws IOException, PowerShellException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("WebApplication", "http://dev");
        params.put("test", "dev");
        params.put("ENV", "dev");

        CommonUtils.executePowerShellScript("20170929113452336_file.ps1", params);

        //Assert.assertEquals("http://dev;dev;dev;test\\agorinenko;False", result);
    }

    @Test
    public void executePowerShellScript4() throws IOException, PowerShellException {
        CommonUtils.executePowerShellScript(SettingsUtils.getBaseSettingsDir() + "/assets/ps/test.ps1", new HashMap<String, Object>());

        //Assert.assertEquals("1", result);
    }

    @Test
    public void executePowerShellScript5() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "ps", "v:20170929113452336", "env:dev" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }

    @Test
    public void executePowerShellScript6() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "ps", "v:20170929113452336", "env:dev" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print("executePowerShellScript6---->"+message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
        //assertEquals("http://dev;dev;dev;dev\agorinenko", message);
    }
    @Test
    public void executePowerShellScript7() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "ps", "v:20170929113452337" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print("executePowerShellScript7---->"+message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
        //assertEquals("http://dev;dev;dev;dev\agorinenko", message);
    }

    @Test
    public void executePowerShellScript8() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, ConfigurationException, IllegalAccessException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "ps", "v:20170929113452338" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print("executePowerShellScript8---->" + message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof ErrorResult);
        assertTrue(message.length() > 0);
    }
}
