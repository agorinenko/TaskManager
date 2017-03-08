package ru.taskmanager.config;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import ru.taskmanager.errors.ConfigurationException;
import ru.taskmanager.utils.StringUtils;
import ru.taskmanager.utils.XmlUtils;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class XmlConfiguration implements Configuration {
    private Map<String, Map<String, String>> dictionary;
    private Document xDoc;

    public XmlConfiguration(Document xDoc){
        dictionary = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        this.xDoc = xDoc;
    }

    @Override
    public Object getEntityByKey(String key) {
        return dictionary.get(key);
    }

    @Override
    public void load() throws ConfigurationException {
        dictionary.clear();

        try {
            NodeList nodes = XmlUtils.selectNodeList(this.xDoc, "//config/setting");
            XmlUtils.eachByNodeList(nodes, node -> {
                String key = XmlUtils.getAttributeValue(node, "key");
                if(!StringUtils.isNullOrEmpty(key)){
                    Map<String, String> map = XmlUtils.attributesToMap(node);
                    dictionary.put(key, map);
                }
            });
        } catch (XPathExpressionException e) {
            throw new ConfigurationException("Элемент //config/setting не найден");
        }
    }
}
