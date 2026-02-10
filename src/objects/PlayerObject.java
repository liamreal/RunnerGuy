package objects;
import display.TextureLoader;
import util.Point3f;

public class PlayerObject extends GameObject {
    // will have some data specific to player and inherit superclass methods
    public PlayerObject() {  
        super();
        this.setTexture();
	}
    public PlayerObject(String textureLocation, int width,int height,Point3f centre) { 
        super(textureLocation, width, height, centre);
	}
    // auto-build texture based on file path pre-specified field (the way i will use for base folder structure)
    public PlayerObject(int width,int height,Point3f centre) { 
        super(width, height, centre);
        // set player texture
        this.setTexture();
	}
    // set text specifically for player
    protected void setTexture() {
        super.setTexture(String.format(
            "%s/textures/player/player.png", 
            TextureLoader.getAssetsPath()
        ));
    }
}
