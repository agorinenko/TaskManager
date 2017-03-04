package ru.taskmanager.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XmlUtils {

    public static Document getXmlDocument(String path) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new FileInputStream(path));

        return document;
    }

    public static Boolean selectBoolean(Document xmlDocument, String expression) throws XPathExpressionException {
        return (Boolean) selectObject(xmlDocument, expression, XPathConstants.BOOLEAN);
    }

    public static Number selectNumber(Document xmlDocument, String expression) throws XPathExpressionException {
        return (Number) selectObject(xmlDocument, expression, XPathConstants.NUMBER);
    }

    public static Node selectSingleNode(Document xmlDocument, String expression) throws XPathExpressionException {
        return (Node) selectObject(xmlDocument, expression, XPathConstants.NODE);
    }

    public static NodeList selectNodeList(Document xmlDocument, String expression) throws XPathExpressionException {
        return (NodeList) selectObject(xmlDocument, expression, XPathConstants.NODESET);
    }

    public static String selectString(Document xmlDocument, String expression) throws XPathExpressionException {
        return (String) selectObject(xmlDocument, expression, XPathConstants.STRING);
    }

    public static Object selectObject(Document xmlDocument, String expression, QName returnType) throws XPathExpressionException {
        XPath xPath =  XPathFactory.newInstance().newXPath();

        return xPath.compile(expression).evaluate(xmlDocument, returnType);
    }

    public static void eachByNodeList(NodeList nodes, NodeListIterator iterator){
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            iterator.doNode(node);
        }
    }

    public static String getAttributeValue(Node node, String attributeName){
        NamedNodeMap attributes = node.getAttributes();
        if (null != attributes) {
            Node namedItem = attributes.getNamedItem(attributeName);
            if(null != namedItem){
                return namedItem.getNodeValue();
            }
        }

        return "";
    }

    public static Map<String, String> attributesToMap(Node node){
        Map<String, String> map = new HashMap<>();
        NamedNodeMap attributes = node.getAttributes();
        if (null != attributes) {
            for (int i = 0; i < attributes.getLength(); i++) {
                Node attribute = attributes.item(i);
                String nodeName = attribute.getNodeName();

                if(!StringUtils.isNullOrEmpty(nodeName)){
                    map.put(nodeName, attribute.getNodeValue());
                }
            }
        }

        return map;
    }

    public interface NodeListIterator{
        void doNode(Node node);
    }
}
