error id: file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/EnemyObject.java:java/util/concurrent/ThreadLocalRandom#nextInt(+2).
file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/EnemyObject.java
empty definition using pc, found symbol in pc: java/util/concurrent/ThreadLocalRandom#nextInt(+2).
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1815
uri: file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/EnemyObject.java
text:
```scala
package objects;
import java.util.concurrent.ThreadLocalRandom;

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
        // enemies will spawn randomly on x-axis
        int enemyWidth = this.getWidth();
        int minX = this.getWidth();
        int maxX = GameDisplay.getDisplayX() - this.getWidth();
        int randomX = (int) ((Math.random() * (maxX - minX)) + minX);
        ThreadLocalRandom.current().nextInt@@(min, max);
        super.setCentre(new Point3f(randomX, -this.getWidth(), 0));
    }

    
		// EnemiesList.add(new EnemyObject(50,50,new Point3f(((float)Math.random()*50+500 ),0,0)));
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: java/util/concurrent/ThreadLocalRandom#nextInt(+2).