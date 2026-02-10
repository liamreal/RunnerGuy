package enums;

public enum PlayerType {
    // different types of players (1 and 2 so far)
    ONE("one"),
    TWO("two");

    // string representation
    private final String type;

    // constructor for each EnemyType (used when Java constructs above enemy types)
    PlayerType(String type) {
        this.type = type;
    }

    // get string representation (for texture path)
    public String toString() {
        return type;
    }
}
