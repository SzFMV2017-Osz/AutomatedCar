package hu.oe.nik.szfmv.environment.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    public static final Logger log = LogManager.getLogger(XmlParser.class);

    final static XPath xpath = XPathFactory.newInstance().newXPath();

    /**
     * 
     * @param filename
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws XPathExpressionException
     * @throws IllegalArgumentException
     */
    public static List<XmlObject> parse(String filename)
            throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        log.info("Start parsing xml: " + filename);

        List<XmlObject> result = new ArrayList<>();
        File xmlFile = new File(ClassLoader.getSystemResource(filename).getFile());
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

        NodeList entriesNodeList = (NodeList) xpath.evaluate("//Scene/Objects/Object", document,
                XPathConstants.NODESET);

        for (int i = 0; i < entriesNodeList.getLength(); i++) {
            Node objectNode = entriesNodeList.item(i);

            String type = (String) xpath.evaluate("@type", objectNode, XPathConstants.STRING);
            String x = (String) xpath.evaluate("Position/@x", objectNode, XPathConstants.STRING);
            String y = (String) xpath.evaluate("Position/@y", objectNode, XPathConstants.STRING);
            double m11 = (double) xpath.evaluate("Transform/@m11", objectNode, XPathConstants.NUMBER);
            double m12 = (double) xpath.evaluate("Transform/@m12", objectNode, XPathConstants.NUMBER);
            double m21 = (double) xpath.evaluate("Transform/@m21", objectNode, XPathConstants.NUMBER);
            double m22 = (double) xpath.evaluate("Transform/@m22", objectNode, XPathConstants.NUMBER);
            double[][] matrix = { { m11, m12 }, { m21, m22 } };
            try {
                result.add(XmlObject.builder().type(type).position(x, y).rotation(matrix).build());
            } catch (IllegalArgumentException e) {
                // TODO: nice to have: the line number where the exception
                // occured;
                log.error("error while parsing xml: " + filename + " " + e.getMessage());
                throw e;
            }
        }
        log.info("Resultset is created with " + result.size() + " number of instances");
        return result;
    }

    public static int[] getWorldDimensions() {
        // TODO: read from XML
        return new int[]{ 5120,3000};
    }
}
