package enums;

import java.util.Arrays;
import java.util.List;

public enum BulletType {
    // different types of bullets
    KILL("kill"),
    EXPLODE("explode");

    // string representation
    private final String type;

    // constructor for each BulletType (used when Java constructs above bullet types)
    BulletType(String type) {
        this.type = type;
    }

    // string of all bullet types
    public static List<BulletType> getAllBulletTypes() {
        return Arrays.asList(BulletType.values());
    }

    // get string representation (for texture path)
    public String toString() {
        return type;
    }
}
