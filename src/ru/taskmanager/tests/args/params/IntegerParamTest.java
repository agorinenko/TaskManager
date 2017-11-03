package args.params;

import org.junit.Test;
import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.args.params.BaseParam;
import ru.taskmanager.args.params.IntegerParam;

import static org.junit.Assert.*;

public class IntegerParamTest {
    @Test
    public void getValue() throws Exception {
        BaseParam param = new IntegerParam("p1:1");
        String key = param.getKey();
        String stringValue = param.getStringValue();
        Object value = param.getValue();
        int intValue = ((IntegerParam)param).getIntValue();

        assertEquals(key, "p1");
        assertEquals(stringValue, "1");
        assertTrue(value instanceof Integer);
        assertEquals(intValue, 1);
    }

    @Test(expected=NumberFormatException.class)
    public void testException3() throws StringIsEmptyException, CorruptedParamException {
        BaseParam param = new IntegerParam("p1:1sss");
        param.getValue();
    }
}