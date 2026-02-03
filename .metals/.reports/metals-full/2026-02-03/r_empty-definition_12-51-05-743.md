error id: file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/PlayerObject.java:_empty_/TextureLoader#getAssetsPath#
file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/PlayerObject.java
empty definition using pc, found symbol in pc: _empty_/TextureLoader#getAssetsPath#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 433
uri: file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/PlayerObject.java
text:
```scala
package objects;
import display.TextureLoader;
import util.GameObject;
import util.Point3f;

public class PlayerObject extends GameObject {

    // will have some data specific to player and inherit superclass methods
    

    public PlayerObject() {  
        super();
	}
    public PlayerObject(int width,int height,Point3f centre) { 
        String textureLocation = String.format("%s/textures/player", TextureLoader.getAssetsPat@@h());
        super(textureLocation, width, height, centre);
	}



  
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/TextureLoader#getAssetsPath#