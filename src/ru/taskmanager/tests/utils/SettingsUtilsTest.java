package utils;

import org.junit.Assert;
import org.junit.Test;
import ru.taskmanager.config.EnvironmentVariables;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.SettingsUtils;

import java.util.Map;

/**
 * Created by agorinenko on 15.11.2017.
 */
public class SettingsUtilsTest {
    @Test
    public void getSettingsUtilsTest() throws ConfigurationException {
        EnvironmentVariables.getInstance().setEnvironmentParameter("dev");

        Double language = (Double) SettingsUtils.getSettingsValue("language");

        Assert.assertTrue(language instanceof Double);
        Assert.assertTrue(language == 1049.1234);


        String dbName = (String) SettingsUtils.getSettingsValue("db.name");

        Assert.assertTrue(dbName instanceof String);
        Assert.assertTrue(dbName.equals("tests"));

        String dbUrl = (String) SettingsUtils.getSettingsValue("db.url");

        Assert.assertTrue(dbUrl instanceof String);
        Assert.assertTrue(dbUrl.equals("jdbc:postgresql://localhost:5432/"));
    }
}
