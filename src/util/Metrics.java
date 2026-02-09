package util;

public class Metrics {
    // find squared distance between 2 objects
    public static float computeSquaredDistance(GameObject thisObject, GameObject thatObject) {
        // get required params
        Point3f thisObjectCentre = thisObject.getCentre();
        Point3f thatObjectCentre = thatObject.getCentre();
        float distanceX = thatObjectCentre.getX() - thisObjectCentre.getX();
        float distanceY = thatObjectCentre.getY() - thisObjectCentre.getY();
        // compare distance squared
        return distanceX*distanceX + distanceY*distanceY;
    }

}
