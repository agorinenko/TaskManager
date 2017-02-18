package ru.taskmanager.config;

import org.w3c.dom.Document;
import ru.taskmanager.errors.ConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;

public class XmlConfiguration implements Configuration {
    private static volatile XmlConfiguration instance;
    private Map<String, Object> dictionary;

    public XmlConfiguration(Document xDoc){
        dictionary = new HashMap<>();
    }

    public static XmlConfiguration getInstance(Document xDoc) {
        XmlConfiguration localInstance = instance;
        if (localInstance == null) {
            synchronized (XmlConfiguration.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new XmlConfiguration(xDoc);
                }
            }
        }

        return localInstance;
    }

    @Override
    public Object getEntityByKey(String key) {
        return dictionary.get(key);
    }

    @Override
    public void load() throws ConfigurationException {
        dictionary.clear();

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        try {
            XPathExpression expr = xpath.compile("//config");
        } catch (XPathExpressionException e) {
            throw new ConfigurationException("Элемент config не найден");
        }
    }
}
