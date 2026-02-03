error id: file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/PlayerObject.java:_empty_/TextureLoader#
file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/PlayerObject.java
empty definition using pc, found symbol in pc: _empty_/TextureLoader#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 297
uri: file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/PlayerObject.java
text:
```scala
package objects;
import display.TextureLoader;
import util.GameObject;
import util.Point3f;

public class PlayerObject extends GameObject {

    // will have some data specific to player and inherit superclass methods
    private String textureLocation = String.format("%s/textures/player", Textur@@eLoader.getAssetsPath());
    hasTexture=true;

    public PlayerObject() {  
        super();
	}
    
    
    public PlayerObject(String textureLocation, int width,int height,Point3f centre) { 
        super(textureLocation, width, height, centre);
	}
    // auto-build texture based on file path pre-specified field (the way i will use for base folder structure)
    public PlayerObject(int width,int height,Point3f centre) { 
        super(width, height, centre);
	}



  
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/TextureLoader#