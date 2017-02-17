package ru.taskmanager.tests;

import org.junit.Test;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.args.errors.RequiredParamException;
import ru.taskmanager.args.params.CommandParam;
import ru.taskmanager.args.params.IntegerParam;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.args.params.StringParam;

import java.util.List;

import static org.junit.Assert.*;

public class ParamsManagerTest {
    @Test
    public void keyExist() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "c1", "p1:1", "p2:v2" });

        assertTrue(manager.keyExist("c1"));
        assertTrue(manager.keyExist("p1"));
        assertFalse(manager.keyExist("p4"));
    }

    @Test
    public void setRequiredParams() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "c1", "p1:1", "p2:v2" });
        manager.setRequiredParams(new String[]{ "c1", "p1" });

        List<CommandParam> commands = manager.getCommandParams();
        List<KeyValueParam> params = manager.getKeyValueParams();

        assertTrue(commands.size() > 0);
        assertTrue(params.size() > 0);
    }

    @Test(expected=RequiredParamException.class)
    public void setRequiredParams2() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "c1", "p1:1", "p2:v2" });
        manager.setRequiredParams(new String[]{ "c1", "c2", "c2"});

        List<CommandParam> commands = manager.getCommandParams();
    }

    @Test(expected=RequiredParamException.class)
    public void setRequiredParams3() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "c1", "p1:1", "p2:v2" });
        manager.setRequiredParams(new String[]{ "p1", "p3", "p1" });

        List<KeyValueParam> params = manager.getKeyValueParams();
    }

    @Test
    public void getKeyValueParams() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "c1", "c2", "c3", "p1:1", "p2:v2" });
        List<KeyValueParam> params = manager.getKeyValueParams();
        assertTrue(params.size() == 2);

        KeyValueParam p1 = params.get(0);
        KeyValueParam p2 = params.get(1);
        assertTrue(p1 instanceof IntegerParam);
        assertTrue(p2 instanceof StringParam);

        assertEquals(p1.getKey(), "p1");
        assertEquals(p2.getKey(), "p2");
    }

    @Test
    public void getCommandParams() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "c1", "c2", "c3", "p1:1", "p2:v2" });
        List<CommandParam> params = manager.getCommandParams();
        assertTrue(params.size() == 3);

        CommandParam p1 = params.get(0);
        CommandParam p2 = params.get(1);
        CommandParam p3 = params.get(2);
        assertTrue(p1 instanceof CommandParam);
        assertTrue(p2 instanceof CommandParam);
        assertTrue(p3 instanceof CommandParam);

        assertEquals(p1.getKey(), "c1");
        assertEquals(p2.getKey(), "c2");
        assertEquals(p3.getKey(), "c3");
    }

}