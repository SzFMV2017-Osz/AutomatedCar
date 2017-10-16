package hu.oe.nik.szfmv.environment;

import java.util.ArrayList;
import java.util.List;

public class XmlScene {
    private int height = 0;
    private int width = 0;
    private List<XmlObject> objectList = new ArrayList<>();

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<XmlObject> getObjectList() {
        return objectList;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setObjectList(List<XmlObject> objectList) {
        this.objectList = objectList;
    }
}
