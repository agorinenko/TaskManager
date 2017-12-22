package args;

import org.junit.Test;
import ru.taskmanager.args.ParamsManager;
import ru.taskmanager.errors.RequiredParamException;
import ru.taskmanager.args.params.CommandParam;
import ru.taskmanager.args.params.IntegerParam;
import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.args.params.StringParam;
import ru.taskmanager.errors.UniqueParamException;

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
    public void getKeyValueParam() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "c1", "c2", "c3", "p1:1", "p2:v2" });

        KeyValueParam p1 = manager.getKeyValueParam("p1");
        KeyValueParam p2 = manager.getKeyValueParam("p2");
        assertTrue(p1 instanceof IntegerParam);
        assertTrue(p2 instanceof StringParam);

        assertEquals(p1.getValue(), 1);
        assertEquals(p2.getValue(), "v2");
    }

    @Test
    public void dbParams() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "db", "o:init", "db.url:jdbc:postgresql://localhost:5432/", "db.name:test_db1", "db.user:postgres", "db.pwd:MyPassword", "db.separator:--SEP--" });

        KeyValueParam p1 = manager.getKeyValueParam("db.url");
        KeyValueParam p2 = manager.getKeyValueParam("db.name");
        KeyValueParam p3 = manager.getKeyValueParam("db.user");
        KeyValueParam p4 = manager.getKeyValueParam("db.pwd");
        KeyValueParam p5 = manager.getKeyValueParam("db.separator");
        assertTrue(p1 instanceof StringParam);
        assertTrue(p2 instanceof StringParam);
        assertTrue(p3 instanceof StringParam);
        assertTrue(p4 instanceof StringParam);
        assertTrue(p5 instanceof StringParam);

        assertEquals(p1.getValue(), "jdbc:postgresql://localhost:5432/");
        assertEquals(p2.getValue(), "test_db1");
        assertEquals(p3.getValue(), "postgres");
        assertEquals(p4.getValue(), "MyPassword");
        assertEquals(p5.getValue(), "--SEP--");
    }

    @Test
    public void mergeKeyValueParams() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "c1", "c2", "c3", "p1:1", "p2:v2", "p4:2", "env:dev" });

        assertEquals(manager.envPresent(), true);

        KeyValueParam p1 = manager.getKeyValueParam("p1");
        KeyValueParam p2 = manager.getKeyValueParam("p2");
        KeyValueParam p3 = manager.getKeyValueParam("p3");
        KeyValueParam p4 = manager.getKeyValueParam("p4");
        assertTrue(p1 instanceof IntegerParam);
        assertTrue(p2 instanceof StringParam);
        assertTrue(p3 instanceof StringParam);
        assertTrue(p4 instanceof IntegerParam);

        assertEquals(p1.getValue(), 1);
        assertEquals(p2.getValue(), "v2");
        assertEquals(p3.getValue(), "p3_dev");
        assertEquals(p4.getValue(), 2);
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

    @Test(expected=UniqueParamException.class)
    public void checkUniqueParams() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "c1", "p1:1", "p1:2", "p2:v2" });

        List<KeyValueParam> params = manager.getKeyValueParams();
    }
    @Test
    public void checkUniqueParams2() throws Exception {
        ParamsManager manager = new ParamsManager(new String[]{ "c1", "p4:p4_dev", "env:dev" });

        KeyValueParam param = manager.getKeyValueParam("p4");

        assertEquals(param.getKey(), "p4");
        assertEquals(param.getStringValue(), "p4_dev");
    }
}