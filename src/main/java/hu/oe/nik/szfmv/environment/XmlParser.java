package hu.oe.nik.szfmv.environment;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Based on https://stackoverflow.com/a/2856812/4737417
 */
public class XmlParser {

    final static XPath xpath = XPathFactory.newInstance().newXPath();

    public static List<XmlObject> parse(String filename) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        List<XmlObject> result = new ArrayList<>();
        File xmlFile = new File(ClassLoader.getSystemResource(filename).getFile());
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

        NodeList entriesNodeList = (NodeList) xpath.evaluate("//Scene/Objects/Object", document, XPathConstants.NODESET);

        getObjects(entriesNodeList, result);
        return result;
    }

    public static XmlScene parseAsXmlScene(String filename) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        XmlScene result = new XmlScene();
        File xmlFile = new File(ClassLoader.getSystemResource(filename).getFile());
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

        String height = (String) xpath.evaluate("//Scene/@height", document, XPathConstants.STRING);
        String width = (String) xpath.evaluate("//Scene/@width", document, XPathConstants.STRING);

        result.setHeight(Integer.parseInt(height));
        result.setWidth(Integer.parseInt(width));

        NodeList entriesNodeList = (NodeList) xpath.evaluate("//Scene/Objects/Object", document, XPathConstants.NODESET);

        getObjects(entriesNodeList, result.getObjectList());

        return result;
    }

    private static void getObjects(NodeList nodeList, List<XmlObject> objectList) throws XPathExpressionException {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node objectNode = nodeList.item(i);

            String type = (String) xpath.evaluate("@type", objectNode, XPathConstants.STRING);
            String x = (String) xpath.evaluate("Position/@x", objectNode, XPathConstants.STRING);
            String y = (String) xpath.evaluate("Position/@y", objectNode, XPathConstants.STRING);
            double m11 = (double) xpath.evaluate("Transform/@m11", objectNode, XPathConstants.NUMBER);
            double m12 = (double) xpath.evaluate("Transform/@m12", objectNode, XPathConstants.NUMBER);
            double m21 = (double) xpath.evaluate("Transform/@m21", objectNode, XPathConstants.NUMBER);
            double m22 = (double) xpath.evaluate("Transform/@m22", objectNode, XPathConstants.NUMBER);
            double[][] matrix = {{m11, m12}, {m21, m22}};
            objectList.add(new XmlObject(type, Integer.parseInt(x), Integer.parseInt(y), matrix));
        }
    }
}
