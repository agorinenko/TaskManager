package config;

import org.junit.Assert;
import org.junit.Test;
import ru.taskmanager.config.EnvironmentVariables;
import ru.taskmanager.errors.ConfigurationException;

import java.util.Map;

/**
 * Created by agorinenko on 15.11.2017.
 */
public class JsonConfigurationTest {
    @Test
    public void LoadEnvironmentVariablesTest() throws ConfigurationException {
        Map<String, Object> items =  EnvironmentVariables.getInstance().getEntityByKey("dev");
        Double key = (Double) items.get("language");

        Assert.assertTrue(key instanceof Double);
        Assert.assertTrue(key == 1049.1234);
    }
}
