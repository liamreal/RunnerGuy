package display;

// will be responsible for obtaining the directory whose textures are used
public class TextureLoader {
    private static String gameType = "default";

    public static String getGameType() {return gameType;}
    public static void setGameType(String newGameType) {gameType = newGameType;}
}
