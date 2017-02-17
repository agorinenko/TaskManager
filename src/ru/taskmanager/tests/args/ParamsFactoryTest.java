package ru.taskmanager.tests.args;

import org.junit.Test;
import ru.taskmanager.args.ParamsFactory;
import ru.taskmanager.args.params.BaseParam;
import ru.taskmanager.args.params.CommandParam;
import ru.taskmanager.args.params.IntegerParam;
import ru.taskmanager.args.params.StringParam;

import static org.junit.Assert.*;


public class ParamsFactoryTest {
    @Test
    public void create() throws Exception {
        ParamsFactory factory = new ParamsFactory();

        BaseParam p1 = factory.create("c1");
        assertTrue(p1 instanceof CommandParam);

        BaseParam p2 = factory.create("p1:1");
        assertTrue(p2 instanceof IntegerParam);

        BaseParam p3 = factory.create("p2:v2");
        assertTrue(p3 instanceof StringParam);
    }

}