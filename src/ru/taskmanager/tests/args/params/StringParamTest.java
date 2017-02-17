package ru.taskmanager.tests.args.params;

import org.junit.Test;
import ru.taskmanager.errors.CorruptedParamException;
import ru.taskmanager.errors.StringIsEmptyException;
import ru.taskmanager.args.params.BaseParam;
import ru.taskmanager.args.params.StringParam;

import static org.junit.Assert.*;

public class StringParamTest {
    @Test
    public void getValue() throws Exception {
        BaseParam param = new StringParam("p1:v1");
        String key = param.getKey();
        String stringValue = param.getStringValue();
        Object value = param.getValue();

        assertEquals(key, "p1");
        assertEquals(stringValue, "v1");
        assertEquals(value, "v1");
    }

    @Test
    public void getValue2() throws Exception {
        BaseParam param = new StringParam("p1:v1:v2");
        String key = param.getKey();
        String stringValue = param.getStringValue();
        Object value = param.getValue();

        assertEquals(key, "p1");
        assertEquals(stringValue, "v1:v2");
        assertEquals(value, "v1:v2");
    }

    public void getValue3() throws Exception {
        BaseParam param = new StringParam(" p1 : v1 ");
        String key = param.getKey();
        String stringValue = param.getStringValue();
        Object value = param.getValue();

        assertEquals(key, "p1");
        assertEquals(stringValue, "v1");
        assertEquals(value, "v1");
    }

    @Test(expected=StringIsEmptyException.class)
    public void testException() throws StringIsEmptyException, CorruptedParamException {
        BaseParam param2 = new StringParam("");
    }

    @Test(expected=CorruptedParamException.class)
    public void testException2() throws StringIsEmptyException, CorruptedParamException {
        BaseParam param2 = new StringParam("command");
    }

    @Test(expected=CorruptedParamException.class)
    public void testException3() throws StringIsEmptyException, CorruptedParamException {
        BaseParam param1 = new StringParam(" :v1");
    }
    @Test(expected=CorruptedParamException.class)
    public void testException4() throws StringIsEmptyException, CorruptedParamException {
        BaseParam param2 = new StringParam(":v1");
    }

    @Test(expected=StringIsEmptyException.class)
    public void testException5() throws StringIsEmptyException, CorruptedParamException {
        BaseParam param1 = new StringParam("p1: ");
    }

    @Test(expected=StringIsEmptyException.class)
    public void testException6() throws StringIsEmptyException, CorruptedParamException {
        BaseParam param1 = new StringParam("p1:");
    }
}