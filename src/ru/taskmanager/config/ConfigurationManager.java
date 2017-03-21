package ru.taskmanager.config;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.StringUtils;
import ru.taskmanager.utils.XmlUtils;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public abstract class ConfigurationManager {
    private XmlConfiguration xmlConfiguration;

    protected void load() throws ConfigurationException {
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

        xmlConfiguration = new XmlConfiguration(xDoc);
        xmlConfiguration.load();
    }

    protected abstract String getAppConfig();

    protected String getBaseDir(){
        return StringUtils.trimEnd(System.getProperty("user.dir"), "//") + "//settings";
    }

    public Map<String, String> getEntityByKey(String key) {
        return (Map<String, String>)xmlConfiguration.getEntityByKey(key);
    }
}
