error id: file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/EnemyObject.java:_empty_/TextureLoader#getAssetsPath#
file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/EnemyObject.java
empty definition using pc, found symbol in pc: _empty_/TextureLoader#getAssetsPath#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1127
uri: file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/EnemyObject.java
text:
```scala
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
        // set player texture
        setTexture(String.format(
            "%s/textures/enemies/%s/%s.png", 
            TextureLoader.getAssetsPath@@()
        ));
	} 
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/TextureLoader#getAssetsPath#