// package enums;
// import util.Vector3f;
// import user.Config;

// public enum Direction {
//     UP(new Vector3f(0,Config.moveSpeed,0)),
//     DOWN(new Vector3f(0,-Config.moveSpeed,0)),
//     LEFT(new Vector3f(-Config.moveSpeed,0,0)),
//     RIGHT(new Vector3f(Config.moveSpeed,0,0));

//     // string representation
//     private final String type;

//     // constructor for each EnemyType (used when Java constructs above enemy types)
//     Direction(String type) {
//         this.type = type;
//     }

//     // get string representation (for texture path)
//     public String toString() {
//         return type;
//     }
// }

package enums;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    DOWN,
    UP,
    LEFT,
    RIGHT;
    
    // string of all directions
    public static List<Direction> getAllDirections() {
        return Arrays.asList(Direction.values());
    }

    @Override
    public String toString() {
        return name();
    }
}
