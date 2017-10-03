package hu.oe.nik.szfmv.filereader.impl;

import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.filereader.XmlReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class XmlReaderImpl implements XmlReader{

    @Override
    public ArrayList<WorldObject> GetAllElements() {

        ArrayList<WorldObject> worldObjects = new ArrayList<WorldObject>();

        try {

            File fXmlFile = new File("test_world.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Object");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

               // System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String img = eElement.getAttribute("type" + ".png");
                    Integer x = Integer.parseInt((eElement.getElementsByTagName("Position").item(0).getAttributes().item(0).getNodeValue()));
                    Integer y = Integer.valueOf((eElement.getElementsByTagName("Position").item(0).getAttributes().item(1).getNodeValue()));

                    WorldObject w = new WorldObject(x, y, img);
                    w.setRotation(0);

                    /*
                    System.out.println("Object type : " + eElement.getAttribute("type"));
                    System.out.println("PositionX : " + eElement.getElementsByTagName("Position").item(0).getAttributes().item(0).getNodeValue());
                    System.out.println("PositionY : " + eElement.getElementsByTagName("Position").item(0).getAttributes().item(1).getNodeValue());

                    System.out.println("Transformation11 : " + eElement.getElementsByTagName("Transform").item(0).getAttributes().item(0));
                    System.out.println("Transformation12 : " + eElement.getElementsByTagName("Transform").item(0).getAttributes().item(1));
                    System.out.println("Transformation21 : " + eElement.getElementsByTagName("Transform").item(0).getAttributes().item(2));
                    System.out.println("Transformation22 : " + eElement.getElementsByTagName("Transform").item(0).getAttributes().item(3));

                    NodeList parameters = eElement.getElementsByTagName("Parameter");
                    for (int i = 0; i < parameters.getLength(); i++) {
                        System.out.println("Parameter1       : " + parameters.item(i).getAttributes().item(0) + " " + parameters.item(i).getAttributes().item(1));
                    }
                    */

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return worldObjects;
    }
}
