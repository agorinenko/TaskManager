package args.params;

import org.junit.Test;
import ru.taskmanager.args.params.BaseParam;
import ru.taskmanager.args.params.BooleanParam;
import ru.taskmanager.args.params.IntegerParam;
import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.StringIsEmptyException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BooleanParamTest {
    @Test
    public void getValue() throws Exception {
        BaseParam param = new BooleanParam("p1:true");
        String key = param.getKey();
        String stringValue = param.getStringValue();
        Object value = param.getValue();
        boolean booleanValue = ((BooleanParam)param).getBooleanValue();

        assertEquals(key, "p1");
        assertEquals(stringValue, "true");
        assertTrue(value instanceof Boolean);
        assertEquals(booleanValue, true);
    }
    @Test
    public void getValue2() throws StringIsEmptyException, CorruptedParamException {
        BooleanParam param = new BooleanParam("p1:1sss");
        boolean booleanValue = param.getBooleanValue();
        assertEquals(booleanValue, false);
    }
}