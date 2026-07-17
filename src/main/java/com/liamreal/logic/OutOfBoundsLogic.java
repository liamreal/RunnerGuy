// package com.liamreal.logic;

// import com.liamreal.display.GameDisplay;
// import com.liamreal.logic.object.ObjectLogic;
// import com.liamreal.objects.GameObject;
// import com.liamreal.util.Point3f;

// public class OutOfBoundsLogic {
    
//     public static boolean isOutOfBounds(ObjectLogic objectLogic) {
//         GameObject gameObject = objectLogic.getGameObject();
//         Point3f objectCentre = gameObject.getCentre();
//         float objectX = objectCentre.getX();
//         float objectY = objectCentre.getY();

//         // case out of bounds down
//         if (objectY >= GameDisplay.getDisplayY()) {
//             return true;
//         }
//         // case out of bounds up
//         if (objectY <= -gameObject.getHeight()) {
//             return true;
//         }
//         // case out of bounds right
//         if (objectX >= GameDisplay.getDisplayX()) {
//             return true;
//         }
//         // case out of bounds left
//         if (objectX <= -gameObject.getWidth()) {
//             return true;
//         }
//         // otherwise not out of bounds
//         return false;
//     }
// }
