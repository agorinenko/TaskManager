package args.commands.imp;

import org.junit.Test;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.*;
import ru.taskmanager.output.ConsolePrinter;

import java.util.List;

import static org.junit.Assert.assertTrue;


public class PlanCommand {
    @Test
    public void init() throws StringIsEmptyException, CorruptedParamException, ClassNotFoundException, InstantiationException, RequiredParamException, IllegalAccessException, ConfigurationException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "plan", "f:plan", "env:dev" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.executeAndPrint(new ConsolePrinter());
        String message = result.get(0).getMessage();


        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }
}
