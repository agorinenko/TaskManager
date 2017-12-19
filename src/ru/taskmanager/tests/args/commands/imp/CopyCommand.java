package args.commands.imp;

import org.junit.Test;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.Executor;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.*;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class CopyCommand {
    @Test
    public void init() throws StringIsEmptyException, RequiredParamException, CorruptedParamException, ClassNotFoundException, InstantiationException, ConfigurationException, IllegalAccessException, UniqueParamException {
        ParamsManager manager = new ParamsManager(new String[]{ "copy", "from:C:\\test", "to:C:\\test1" });

        Executor executor = new Executor(manager);
        List<CommandResult> result = executor.execute();
        String message = result.get(0).getMessage();

        System.out.print(message);

        assertTrue(result.size() > 0);
        assertTrue(result.get(0) instanceof SuccessResult);
        assertTrue(message.length() > 0);
    }
}
