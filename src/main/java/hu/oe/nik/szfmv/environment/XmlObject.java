package hu.oe.nik.szfmv.environment;

public class XmlObject {

    private String type = "";
    private int x = 0;
    private int y = 0;
    private double[][] matrix = new double[2][2];

    public XmlObject(String type, int x, int y, double[][] matrix) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.matrix = matrix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double m11, double m12, double m21, double m22) {
        this.matrix = new double[][]{{m11, m12}, {m21, m22}};
    }
}
