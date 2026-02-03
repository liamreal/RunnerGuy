package enums;

public enum EnemyType {
    // different types of enemies
    BASIC("basic"),
    ADVANCED("advanced");

    // string representation
    private final String type;

    // constructor for each EnemyType (used when Java constructs above enemy types)
    EnemyType(String type) {
        this.type = type;
    }

    // get string representation (for texture path)
    public String toString() {
        return type;
    }
}
