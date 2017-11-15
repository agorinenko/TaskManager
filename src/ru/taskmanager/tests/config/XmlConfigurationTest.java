package config;

import org.junit.Assert;
import org.junit.Test;
import ru.taskmanager.config.Commands;
import ru.taskmanager.config.Settings;
import ru.taskmanager.errors.ConfigurationException;

import java.util.Map;

/**
 * Created by agorinenko on 15.11.2017.
 */

public class XmlConfigurationTest {
    @Test
    public void LoadCommandsTest() throws ConfigurationException {
        Map<String, Object> items = Commands.getInstance().getEntityByKey("help");
        String key = (String) items.get("key");
        String value = (String) items.get("class");

        Assert.assertTrue(key.length() > 0);
        Assert.assertTrue(value.length() > 0);
    }

    @Test
    public void LoaSettingsTest() throws ConfigurationException {
        Map<String, Object> items = Settings.getInstance().getEntityByKey("help");
        String key = (String) items.get("key");
        String value = (String) items.get("value");

        Assert.assertTrue(key.length() > 0);
        Assert.assertTrue(value.length() > 0);
    }
}
