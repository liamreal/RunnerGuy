package objects;
import display.GameDisplay;
import display.TextureLoader;
import enums.EnemyType;
import util.GameObject;
import util.Point3f;


public class EnemyObject extends GameObject {

    // will have some data specific to player and inherit superclass methods
    public EnemyObject(EnemyType enemyType) {  
        super();
        this.setCentre();
        // set enemy texture
        this.setTexture(enemyType);
	}
    public EnemyObject(String textureLocation, int width, int height, Point3f centre) { 
        super(textureLocation, width, height, centre);
	}
    // auto-build texture based on file path pre-specified field (the way i will use for base folder structure)
    public EnemyObject(int width,int height, Point3f centre) { 
        super(width, height, centre);
        // set enemy texture
        this.setTexture(EnemyType.BASIC);
	} 
    public EnemyObject(int width,int height, Point3f centre, EnemyType enemyType) { 
        super(width, height, centre);
        // set enemy texture
        this.setTexture(enemyType);
	}
    
    // set text specifically for enemy
    protected void setTexture(EnemyType enemyType) {
        String enemy = enemyType.toString();
        super.setTexture(String.format(
            "%s/textures/enemies/%s/%s.png", 
            TextureLoader.getAssetsPath(),
            enemy,
            enemy
        ));
    }
    protected void setCentre() {
        super.setCentre(new Point3f(GameDisplay.getDisplayX()/2, 0, 0));
    }
}
