package com.liamreal;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.liamreal.assets.AssetBuilder;
import com.liamreal.assets.AssetConfig;
import com.liamreal.display.GameDisplay;
import com.liamreal.ecs.Entity;
import com.liamreal.user.Config;
import com.liamreal.components.graphics.Animation;
import com.liamreal.components.graphics.ImageLoader;
import com.liamreal.components.graphics.SpriteRenderer;
import com.liamreal.components.graphics.SpriteSheet;
import com.liamreal.components.physics.Transform;


/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 
 * Credits: Kelly Charles (2020)
 */ 
public class Viewer extends JPanel {
	private Animation animation;
	private boolean isGameOver = false;
	BufferedImage backgroundImage = ImageLoader.load(String.format(
		"%s/textures/background/background.png", 
		AssetConfig.getAssetsPath().toString()
	));
	private SpriteSheet backgroundSpriteSheet = new SpriteSheet(backgroundImage, GameDisplay.getDisplayX(), GameDisplay.getDisplayY());
	private Entity background = new Entity(0);
	
	
	Model gameworld; 
	Config config = Config.getInstance();
	 
	public Viewer(Model World) {
		this.gameworld=World;
		// create background
		this.background.add(new Transform(0, 0));
		this.background.add(new SpriteRenderer(backgroundSpriteSheet));
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Viewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void updateview() {
		
		this.repaint();
		// TODO Auto-generated method stub
		
	}

	// set game to be over
	public void setGameOver(boolean isGameOver) { this.isGameOver = isGameOver; }
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(config.getResolutionX(), config.getResolutionY());
	}
		
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		animation.incrementAnimationTime();

		//Draw background 
		drawBackground(g);
	
		

		// draw game over if updated to be game over
		if (isGameOver) { this.drawGameOver(g); }
	}

	private void drawBackground(Graphics g)
	{
		background.get(SpriteRenderer.class).drawImage(background.get(Transform.class).getPosition(), g, animation);
		
		// File TextureToLoad = new File(String.format(
        //     "%s/textures/background/background.png", 
        //     AssetLoader.getAssetsPath()
        // ));
		// //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		// try {
		// 	Image myImage = ImageIO.read(TextureToLoad); 
		// 	 g.drawImage(myImage, 0,0, GameDisplay.getDisplayX(), GameDisplay.getDisplayY(), 0 , 0, GameDisplay.getDisplayX(), GameDisplay.getDisplayY(), null); 
			
		// } catch (IOException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }
	}
	
	// private void drawPlayer(ObjectLogic player,Graphics g) { 
	// 	GameObject playerObject = player.getGameObject();
	// 	int x = (int) playerObject.getCentre().getX();
	// 	int y = (int) playerObject.getCentre().getY();
	// 	int width = (int) playerObject.getWidth();
	// 	int height = (int) playerObject.getHeight();
	// 	// System.out.println(texture);
	// 	File TextureToLoad = new File(playerObject.getTexture());  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
	// 	try {
	// 		Image myImage = ImageIO.read(TextureToLoad);
	// 		// read health item texture
	// 		Image healthImage = ImageIO.read(new File(String.format(
    //         "%s/textures/items/health/health.png", 
    //         AssetLoader.getAssetsPath()
    //     	)));
	// 		Image bulletImage = ImageIO.read(new File(String.format(
    //         "%s/textures/items/bullet/bullet.png", 
    //         AssetLoader.getAssetsPath()
    //     	)));
	// 		//The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time 
	// 		//remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31  
	// 		int currentPositionInAnimation= (int) ((CurrentAnimationTime%16/4))*32; //slows down animation so every 10 frames we get another frame so every 100ms 
	// 		g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , 0, currentPositionInAnimation+31, 32, null); 
	// 		// draw heart for player health (above player)
	// 		g.drawImage(healthImage, x+(width/4), (int) (y-(width*2/3.5)), 32, 64, this);

	// 		// draw player health above player using draw string method
	// 		String health = String.format("%d", playerObject.getHealth());
	// 		// draw health (for now fixed black colour)
	// 		g.setFont(new Font("Arial", Font.BOLD, 10));
	// 		g.setColor(Color.WHITE);
	// 		g.drawString(health, x+(width/2), y-(width/4));

	// 		// draw bullet (if player has bullet powerup)
	// 		if (player.getItemType() == ItemType.BULLET) { g.drawImage(bulletImage, x+(width/4), y+height, 32, 64, this); }
	// 	} catch (IOException e) {
	// 		// TODO Auto-generated catch block
	// 		e.printStackTrace();
	// 	} 
		 
	// 	//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer));
	// 	//Lighnting Png from https://opengameart.org/content/animated-spaceships  its 32x32 thats why I know to increament by 32 each time 
	// 	// Bullets from https://opengameart.org/forumtopic/tatermands-art 
	// 	// background image from https://www.needpix.com/photo/download/677346/space-stars-nebula-background-galaxy-universe-free-pictures-free-photos-free-images
		
	// }
	private void drawGameOver(Graphics g) {
		File TextureToLoad = new File("assets/game_over.png"); // game over image 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			g.drawImage(myImage, 0,0, GameDisplay.getDisplayX(), GameDisplay.getDisplayY(), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
		 
	 

}


/*
 * 
 * 
 *              VIEWER HMD into the world                                                             
                                                                                
                                      .                                         
                                         .                                      
                                             .  ..                              
                               .........~++++.. .  .                            
                 .   . ....,++??+++?+??+++?++?7ZZ7..   .                        
         .   . . .+?+???++++???D7I????Z8Z8N8MD7I?=+O$..                         
      .. ........ZOZZ$7ZZNZZDNODDOMMMMND8$$77I??I?+?+=O .     .                 
      .. ...7$OZZ?788DDNDDDDD8ZZ7$$$7I7III7??I?????+++=+~.                      
       ...8OZII?III7II77777I$I7II???7I??+?I?I?+?+IDNN8??++=...                  
     ....OOIIIII????II?I??II?I????I?????=?+Z88O77ZZO8888OO?++,......            
      ..OZI7III??II??I??I?7ODM8NN8O8OZO8DDDDDDDDD8DDDDDDDDNNNOZ= ......   ..    
     ..OZI?II7I?????+????+IIO8O8DDDDD8DNMMNNNNNDDNNDDDNDDNNNNNNDD$,.........    
      ,ZII77II?III??????DO8DDD8DNNNNNDDMDDDDDNNDDDNNNDNNNNDNNNNDDNDD+.......   .
      7Z??II7??II??I??IOMDDNMNNNNNDDDDDMDDDDNDDNNNNNDNNNNDNNDMNNNNNDDD,......   
 .  ..IZ??IIIII777?I?8NNNNNNNNNDDDDDDDDNDDDDDNNMMMDNDMMNNDNNDMNNNNNNDDDD.....   
      .$???I7IIIIIIINNNNNNNNNNNDDNDDDDDD8DDDDNM888888888DNNNNNNDNNNNNNDDO.....  
       $+??IIII?II?NNNNNMMMMMDN8DNNNDDDDZDDNN?D88I==INNDDDNNDNMNNMNNNNND8:..... 
   ....$+??III??I+NNNNNMMM88D88D88888DDDZDDMND88==+=NNNNMDDNNNNNNMMNNNNND8......
.......8=+????III8NNNNMMMDD8I=~+ONN8D8NDODNMN8DNDNNNNNNNM8DNNNNNNMNNNNDDD8..... 
. ......O=??IIIIIMNNNMMMDDD?+=?ONNNN888NMDDM88MNNNNNNNNNMDDNNNMNNNMMNDNND8......
........,+++???IINNNNNMMDDMDNMNDNMNNM8ONMDDM88NNNNNN+==ND8NNNDMNMNNNNNDDD8......
......,,,:++??I?ONNNNNMDDDMNNNNNNNNMM88NMDDNN88MNDN==~MD8DNNNNNMNMNNNDND8O......
....,,,,:::+??IIONNNNNNNDDMNNNNNO+?MN88DN8DDD888DNMMM888DNDNNNNMMMNNDDDD8,.... .
...,,,,::::~+?+?NNNNNNNMD8DNNN++++MNO8D88NNMODD8O88888DDDDDDNNMMMNNNDDD8........
..,,,,:::~~~=+??MNNNNNNNND88MNMMMD888NNNNNNNMODDDDDDDDND8DDDNNNNNNDDD8,.........
..,,,,:::~~~=++?NMNNNNNNND8888888O8DNNNNNNMMMNDDDDDDNMMNDDDOO+~~::,,,.......... 
..,,,:::~~~~==+?NNNDDNDNDDNDDDDDDDDNNND88OOZZ$8DDMNDZNZDZ7I?++~::,,,............
..,,,::::~~~~==7DDNNDDD8DDDDDDDD8DD888OOOZZ$$$7777OOZZZ$7I?++=~~:,,,.........   
..,,,,::::~~~~=+8NNNNNDDDMMMNNNNNDOOOOZZZ$$$77777777777II?++==~::,,,......  . ..
...,,,,::::~~~~=I8DNNN8DDNZOM$ZDOOZZZZ$$$7777IIIIIIIII???++==~~::,,........  .  
....,,,,:::::~~~~+=++?I$$ZZOZZZZZ$$$$$777IIII?????????+++==~~:::,,,...... ..    
.....,,,,:::::~~~~~==+?II777$$$$77777IIII????+++++++=====~~~:::,,,........      
......,,,,,:::::~~~~==++??IIIIIIIII?????++++=======~~~~~~:::,,,,,,.......       
.......,,,,,,,::::~~~~==+++???????+++++=====~~~~~~::::::::,,,,,..........       
.........,,,,,,,,::::~~~======+======~~~~~~:::::::::,,,,,,,,............        
  .........,.,,,,,,,,::::~~~~~~~~~~:::::::::,,,,,,,,,,,...............          
   ..........,..,,,,,,,,,,::::::::::,,,,,,,,,.,....................             
     .................,,,,,,,,,,,,,,,,.......................                   
       .................................................                        
           ....................................                                 
               ....................   .                                         
                                                                                
                                                                                
                                                                 GlassGiant.com
                                                                 */
