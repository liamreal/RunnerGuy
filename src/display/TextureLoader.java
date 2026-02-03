package display;

// will be responsible for obtaining the directory whose textures are used
public class TextureLoader {
    private static String gameType = "default";
    private static String assetsPath = String.format("assets/%s", gameType);

    public static String getGameType() {return gameType;}
    public static String getAssetsPath() {return assetsPath;}

    public static void setGameType(String newGameType) {gameType = newGameType;}

}
