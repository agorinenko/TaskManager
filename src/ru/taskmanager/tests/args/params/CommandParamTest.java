package args.params;

import org.junit.Test;
import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.args.params.BaseParam;
import ru.taskmanager.args.params.CommandParam;

import static org.junit.Assert.*;


public class CommandParamTest {
    @Test
    public void getValue() throws Exception {
        BaseParam param = new CommandParam("db");
        String key = param.getKey();
        String stringValue = param.getStringValue();
        Object value = param.getValue();

        assertEquals(key, "db");
        assertEquals(stringValue, "db");
        assertEquals(value, "db");

        CommandParam param2 = new CommandParam("p1:v1");
        String key2 = param2.getKey();
        String stringValue2 = param2.getStringValue();
        Object value2 = param2.getValue();

        assertEquals(key2, "p1:v1");
        assertEquals(stringValue2, "p1:v1");
        assertEquals(value2, "p1:v1");
    }

    @Test(expected=StringIsEmptyException.class)
    public void testException() throws StringIsEmptyException, CorruptedParamException {
        BaseParam param2 = new CommandParam("");
    }

}