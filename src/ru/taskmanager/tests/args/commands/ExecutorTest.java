package args.commands;

import org.junit.Test;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.ErrorResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExecutorTest {
    @Test
    public void executor() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "TestSuccessCommand", "TestErrorCommand", "p1:Success", "p2:Error" });
        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertEquals(result.get(0).getMessage(), "Success");
        assertTrue(result.get(1) instanceof ErrorResult);
        assertEquals(result.get(1).getMessage(), "Error");
    }

    @Test
    public void executor2() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "TestSuccessCommand".toUpperCase(), "TestErrorCommand".toUpperCase(), "p1:Success", "p2:Error" });
        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertEquals(result.get(0).getMessage(), "Success");
        assertTrue(result.get(1) instanceof ErrorResult);
        assertEquals(result.get(1).getMessage(), "Error");
    }

    @Test
    public void executor3() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "TestSuccessCommand".toLowerCase(), "TestErrorCommand".toLowerCase(), "p1:Success", "p2:Error" });
        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertEquals(result.get(0).getMessage(), "Success");
        assertTrue(result.get(1) instanceof ErrorResult);
        assertEquals(result.get(1).getMessage(), "Error");
    }

    @Test(expected=UniqueParamException.class)
    public void executor4() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "TestSuccessCommand", "TestErrorCommand", "p1:Success", "p1:Success2", "p2:Error" });
        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();

    }

//    @Test
//    public void executor5() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
//        ParamsManager manager = new ParamsManager(new String[]{ "TestSuccessCommand", "TestErrorCommand", "p:Success1", "TestSuccessCommand.p:Success", "p2:Error" });
//        Executor executor = new Executor(manager);
//        List<CommandResult> result = executor.execute();
//
//        assertTrue(result.size() > 0);
//        assertTrue(result.get(0) instanceof SuccessResult);
//        assertEquals("Success", result.get(0).getMessage());
//        assertTrue(result.get(1) instanceof ErrorResult);
//        assertEquals("Error", result.get(1).getMessage());
//    }

}
