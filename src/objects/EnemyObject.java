package objects;
import display.TextureLoader;
import enums.EnemyType;
import util.GameObject;
import util.Point3f;


public class EnemyObject extends GameObject {

    // will have some data specific to player and inherit superclass methods
    public EnemyObject() {  
        super();
	}
    public EnemyObject(String textureLocation, int width,int height, Point3f centre) { 
        super(textureLocation, width, height, centre);
	}
    // auto-build texture based on file path pre-specified field (the way i will use for base folder structure)
    public EnemyObject(int width,int height, Point3f centre) { 
        super(width, height, centre);
        // set player texture
        setTexture(String.format(
            "%s/textures/enemies/basic/basic.png", 
            TextureLoader.getAssetsPath()
        ));
	} 
    public EnemyObject(int width,int height, Point3f centre, EnemyType enemyType) { 
        super(width, height, centre);
        String enemy = enemyType.toString();
        // set player texture
        setTexture(String.format(
            "%s/textures/enemies/%s/%s.png", 
            TextureLoader.getAssetsPath(),
            enemy,
            enemy
        ));
	} 
}
