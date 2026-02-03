error id: file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/EnemyObject.java
file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/EnemyObject.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[25,68]

error in qdox parser
file content:
```java
offset: 893
uri: file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/EnemyObject.java
text:
```scala
package objects;
import display.TextureLoader;
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
    public EnemyObject(int width,int height, Point3f centre, String)@@ { 
        super(width, height, centre);
        // set player texture
        setTexture(String.format(
            "%s/textures/enemies/basic/basic.png", 
            TextureLoader.getAssetsPath()
        ));
	} 
}

```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.mtags.MtagsIndexer.index(MtagsIndexer.scala:22)
	scala.meta.internal.mtags.MtagsIndexer.index$(MtagsIndexer.scala:21)
	scala.meta.internal.mtags.JavaMtags.index(JavaMtags.scala:39)
	scala.meta.internal.mtags.Mtags$.allToplevels(Mtags.scala:155)
	scala.meta.internal.metals.DefinitionProvider.fromMtags(DefinitionProvider.scala:372)
	scala.meta.internal.metals.DefinitionProvider.$anonfun$positionOccurrence$6(DefinitionProvider.scala:291)
	scala.Option.orElse(Option.scala:477)
	scala.meta.internal.metals.DefinitionProvider.$anonfun$positionOccurrence$1(DefinitionProvider.scala:291)
	scala.Option.flatMap(Option.scala:283)
	scala.meta.internal.metals.DefinitionProvider.positionOccurrence(DefinitionProvider.scala:276)
	scala.meta.internal.metals.DefinitionProvider.definitionFromSnapshot(DefinitionProvider.scala:335)
	scala.meta.internal.metals.DefinitionProvider.$anonfun$definition$2(DefinitionProvider.scala:116)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.metals.DefinitionProvider.fromSemanticDb$1(DefinitionProvider.scala:116)
	scala.meta.internal.metals.DefinitionProvider.$anonfun$definition$11(DefinitionProvider.scala:146)
	scala.meta.internal.metals.DefinitionProvider.$anonfun$definition$15(DefinitionProvider.scala:153)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:503)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	java.base/java.lang.Thread.run(Thread.java:1575)
```
#### Short summary: 

QDox parse error in file:///C:/College/Stage_4_Term_2/COMP30540_Game_Development/RunnerGuy/src/objects/EnemyObject.java