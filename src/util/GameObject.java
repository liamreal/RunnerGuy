package util;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.time.Duration;
import java.time.Instant;

import display.GameDisplay;
import display.TextureLoader;
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
 */ 
public class GameObject {
	// by default spawn in middle of screen
	private Point3f centre= GameDisplay.getDisplayCentre();			// by default place object in centre of screen (roughly - places top left corner in centre)
	// by default objects have pixel size of 50
	private int width=50;
	private int height=50;
	private boolean hasTextured=false;
	private String textureLocation; 
	private String blankTexture= String.format("%s/textures/blank.png", TextureLoader.getAssetsPath());
	private int health=1;
	private Instant spawnTime; // used to keep track of when object was spawned (to check if should be killed)
	private double minLifeTime = 0.05; // by default a spawned entity must live minimum amount of time (to prevent invisible bullets)

	public GameObject() {
		this.setSpawnTime();
	}
    public GameObject(String textureLocation,int width,int height,Point3f centre) {
		this.setSpawnTime();
		hasTextured=true;
		this.textureLocation=textureLocation;
		this.width=width;
		this.height=height;
		this.centre =centre;
	}
	public GameObject(int width,int height, Point3f centre) { 
		this.setSpawnTime();
		hasTextured=false;
		this.width=width;
		this.height=height;
		this.centre =centre;
	}
	// note down object spawn time
	private void setSpawnTime() { this.spawnTime = Instant.now(); }
	private Instant getSpawnTime() { return this.spawnTime; }
	private double getMinLifeTime() { return this.minLifeTime; }
	
	public Point3f getCentre() { return centre; }
	public int getHealth() { return this.health; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public String getTexture() {
		if(hasTextured) { return textureLocation; }
		return blankTexture; 
	}
	public void setCentre(Point3f centre) { this.centre = centre; }
	public void setHealth(int newHealth) { this.health = newHealth; }
	// used to update texture within constructors for classes that extend this
	public void setTexture(String newTexture) {
		hasTextured=true;
		textureLocation = newTexture; 
	}

	// decrease health (by default by 1)
    public void decreaseHealth(int healthToDecreaseBy) {
        this.setHealth(this.getHealth()-healthToDecreaseBy);
    }
	// by default decrease health by 1
    public void decreaseHealth() {
		this.decreaseHealth(1);
    }
	public boolean isAlive() {
		double lifeTime = DurationHandler.getDurationSeconds(this.getSpawnTime());
		// check if health is valid OR if has lived for minimum amount of time has to live for
        return (this.getHealth() > 0 || lifeTime < this.getMinLifeTime());
    }

	// by default sort a list of objects relative to this object by distance
	public CopyOnWriteArrayList<GameObject> sortByClosest(CopyOnWriteArrayList<GameObject> otherObjects) {
		if (otherObjects == null || otherObjects.isEmpty()) { return null; }
		// will be our list we sort
		List<GameObject> sortedList = new ArrayList<>(otherObjects);
		// used ChatGPT to help with lambda function sorting, uses my squared distance calculation method as a comparison for sorting
		sortedList.sort(
			Comparator.comparingDouble(otherObject ->
				Metrics.computeSquaredDistance(this, otherObject)
			)
		);
		// return sorted list as a CopyOnWriteArrayList
		return new CopyOnWriteArrayList<>(sortedList);
	}

	public boolean collide(GameObject other) {
		// this object
		Point3f thisCentre = this.getCentre();
		float thisX = thisCentre.getX();
		float thisY = thisCentre.getY();
		float thisWidth = this.getWidth();
		float thisHeight = this.getHeight();
		// other object
		Point3f otherCentre = other.getCentre();
		float otherX = otherCentre.getX();
		float otherY = otherCentre.getY();
		float otherWidth = other.getWidth();
		float otherHeight = other.getHeight();

		// use AABB collision check -- return if true (suggested in slides and seen online)
		return 	thisX < otherX + otherWidth &&
				thisX + thisWidth > otherX &&
				thisY < otherY + otherHeight &&
				thisY + thisHeight > otherY;
	}

	// GameObject to string also returns location on screen
	public String toString() {
		return String.format("%s at (%f,%f)", super.toString(), centre.getX(), centre.getY());
	}
  
}

/*
 *  Game Object 
 * ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::::::c:::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::clc::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::lol:;::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::::::::;;cool:;::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::codk0Oxolc:::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::::::::::::::::::::::::coxk0XNWMMWWWNK0kxdolc::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::loxO0XWMMMMMMMWWWMMMMMMWWNK0Oxdlc::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::cldkOKNWMMMMMMMMMMMMMWWMMMMMMMMMMMMMWNX0Okdolc:::::::::::::::::::::::::::::::::
::::::::::::::::codk0KNWMMMMMMMMMMMMMMMMMMWMMMMMMMMMMMMMMMMMMMMWWXKOkxo:::::::::::::::::::::::::::::
::::::::::::;cdOXNWMMMMMMMMMMMMMMMMMMMMMMWWWMMMMMMMMMMMMMMMMMMMMMMMNKOdc::::::::::::::::::::::::::::
:::::::::::::cxKXXNWWMMMMMMMMMMMMMMMMMMMMWWWMMMMMMMMMMMMMMMMMMMWX0kdolc:::::::::::::::::::::::::::::
::::::::::::::d0000KKXNNWMMMMMMMMMMMMMMMMWWMMMMMMMMMMMMMMMMWNKOxolllllc:::::::::::::::::::::::::::::
::::::::::::::oO00000000KXXNWWMMMMMMMMMMMMWMMMMMMMMMMMMMWX0kdollllllllc:::::::::::::::::::::::::::::
::::::::::::::lk00000O000000KKXNWWMMMMMMMWWWMMMMMMMMWNKOxollllllllllll::::::::::::::::::::::::::::::
::::::::::::::cx0000000000O000000KXXNWMMMWWWMMMMWXK0kdlllllllllllllllc::::::::::::::::::::::::::::::
:::::::::::::::dO00000000000000000000KKXNNNWWNKOxolllllllllllllllllllc::::::::::::::::::::::::::::::
:::::::::::::::lO000000000000000OOOO0000000Kkdlllllllllllllllllllllllc::::::::::::::::::::::::::::::
:::::::::::::::ck00000000000000000OOOOOOOOkxollllllllllllllllllllllll:::::::::::::::::::::::::::::::
:::::::::::::;;cx00000000000000000000OOOOOOxocllllllllllllllllllllllc:;;;;;;;;;;::::::::::::::::::::
;;;;;;;;;;;;;;;:oO00000000000000000OOOO0000kdllllcclllllllllllllllllc:;;;;;;;;;;;;;;;;;;::::::::::::
;;;;;;;;;;;;;;;:lO00000000000000OOO00000000Oolllllllllllllllllllllllc:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;ck0000000000OOO000000000000kolllllllllllllllllllllll:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;:dOO0000OOO0000000000000000kolllllllllllllllllllllllc:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;:lxkOOOO0000000000000000000kolllllllllllllllllllllooool::;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;::;;::cokOkkO00000000000000000000kolllllllllllllllllllllccccllcc::::;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;:;;:;::ccllodxkO00000000000000000000kolllllllllllllllllllcc:;;;;:::::;;:;;;;;;;;;;;;;;;;;;;;;
;;;;;;;::::::::::;;:ldkO0000000000000000000kolllllllllllllllllc::;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;::;;:::;;;;;:;::ldkO0000000000000000kollllllllllllllcc::;;;;:;;:;;;;;:;;;:;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;:;;;;:cldkO0000000000000kollllllllllllc:::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;::ldkO0000000000kolllllllllcc::;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;::;:;;::ldkO0000000kolllllllc::::;;;;:;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;::ldkO0000kolllcc:::;;;;;;;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;;;;;:ldkO0kolcc::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;;;::lodl:::::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;::;:::::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;:;;::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;::;;;;;;;;;;;:::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
*/