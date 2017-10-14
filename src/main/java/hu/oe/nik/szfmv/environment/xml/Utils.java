package hu.oe.nik.szfmv.environment;

public class Utils {

    /**
     * Converts the length definied in pixels to meters according the rule of 350 pixel = 7 meters.
     *
     * @param pixel is the length in pixels
     * @return the length in meters
     */
    public static double convertPixelToMeter(int pixel) {
        return pixel / 50;
    }


    // Copied from https://github.com/SzFMV2017-Tavasz/AutomatedCar/blob/master/src/main/java/hu/oe/nik/szfmv17t/environment/utils/XmlParser.java#L257
    public static double convertMatrixToRadians(double m11, double m12, double m21, double m22) {
        //formula of the angle between the two vectors: a * b = |a| * |b| * cos(beta)
        //where a * b is the scalarProduct
        //Our zero degree will be the horizontal right:
        double defaultX = 1;
        double defaultY = 0;

        double transformedX = m11 * defaultX + m12 * defaultY;
        double transformedY = m21 * defaultX + m22 * defaultY;

        double scalarProduct = defaultX * transformedX + defaultY * transformedY;

        double defaultVectorLength = Math.sqrt(defaultX * defaultX + defaultY * defaultY);
        double transformedVectorLength = Math.sqrt(transformedX * transformedX + transformedY * transformedY);

        double angleInRad = Math.acos(scalarProduct / (defaultVectorLength * transformedVectorLength));
        if (transformedY < 0) {
            angleInRad = 2 * Math.PI - angleInRad;
        }
        //If angle is NaN as a result of transformedVectorLength=0, Math.round() returns 0. It is correct in our cases.
        //angleInRad = Math.round(angleInRad * 100.0) / 100.0;
        return angleInRad;
//        double m13 = 0;
//        double m23 = 0;
//        double m33 = 1;
//        return 6.2831853072d - Math.acos((m11 + m22) / 2);
    }

    public static double convertMatrixToRadians(double[][] matrix) {
        return convertMatrixToRadians(matrix[0][0], matrix[0][1], matrix[1][0], matrix[1][1]);
    }

    public static double radianToDegree(double rad) {
        return (rad * 180) / Math.PI;
    }
}
