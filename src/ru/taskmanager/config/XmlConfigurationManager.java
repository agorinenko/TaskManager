package ru.taskmanager.config;

import org.junit.After;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.SettingsUtils;
import ru.taskmanager.utils.StringUtils;
import ru.taskmanager.utils.XmlUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public abstract class XmlConfigurationManager extends ConfigurationManager {

    protected XmlConfigurationManager() throws ConfigurationException {
    }

    @Override
    protected Configuration createConfiguration() throws ConfigurationException {
        String appConfig = getAppConfig();
        Document xDoc;
        try {
            xDoc = XmlUtils.getXmlDocument(appConfig);
        } catch (ParserConfigurationException e) {
            throw new ConfigurationException("Parser configuration exception:" + e.getMessage());
        } catch (IOException e) {
            throw new ConfigurationException("IO exception:" + e.getMessage());
        } catch (SAXException e) {
            throw new ConfigurationException("SAX exception:" + e.getMessage());
        }

        return new XmlConfiguration(xDoc);
    }
}
