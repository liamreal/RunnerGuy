package com.liamreal.objects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.time.Instant;
import com.liamreal.display.GameDisplay;
import com.liamreal.display.TextureLoader;
import com.liamreal.util.DurationHandler;
import com.liamreal.util.Metrics;
import com.liamreal.util.Point3f;
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
public abstract class GameObject {
	// by default spawn in middle of screen
	private Point3f centre= GameDisplay.getDisplayCentre();			// by default place object in centre of screen (roughly - places top left corner in centre)
	// by default objects have pixel size of 50
	private int width=50;
	private int height=50;
	private boolean hasTextured=false;
	private String textureLocation; 
	private String blankTexture= String.format("%s/textures/blank.png", TextureLoader.getAssetsPath());
	private int health=1;
	private final Instant spawnTime = Instant.now(); // used to keep track of when object was spawned (to check if should be killed)
	private double minLifeTime = 0.2; // by default a spawned entity must live minimum amount of time (to prevent invisible bullets that spawn directly at an enemy)

	public GameObject() {
	}
	public GameObject(Point3f centre) {
		this.centre = centre;
	}
    public GameObject(String textureLocation,int width,int height,Point3f centre) {
		hasTextured=true;
		this.textureLocation=textureLocation;
		this.width=width;
		this.height=height;
		this.centre =centre;
	}
	public GameObject(int width,int height, Point3f centre) { 
		hasTextured=false;
		this.width=width;
		this.height=height;
		this.centre =centre;
	}
	// note down object spawn time, this should NOT be used outside this class 
	private Instant getSpawnTime() { return this.spawnTime; }
	
	public Point3f getCentre() { return centre; }
	public int getHealth() { return this.health; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public String getTexture() {
		if(hasTextured) { return textureLocation; }
		return blankTexture; 
	}
	public double getMinLifeTime() { return this.minLifeTime; }
	public void setCentre(Point3f centre) { this.centre = centre; }
	public void setHealth(int newHealth) { this.health = newHealth; }

	// motivation behind making class abstract, needed a way to say "this method needs to be defined, but only defined in subclasses"
	abstract public void updateTexture();

	// used to update texture within constructors for classes that extend this
	public void setTexture(String newTexture) {
		if (newTexture.equals(this.getTexture())) { return; }
		hasTextured=true;
		textureLocation = newTexture; 
	}
	public void setMinLifeTime(double newMinLifeTime) { this.minLifeTime = newMinLifeTime; }


	// decrease health (by default by 1)
    public void decreaseHealth(int healthToDecreaseBy) {
        this.setHealth(this.getHealth()-healthToDecreaseBy);
    }
	// by default decrease health by 1
    public void decreaseHealth() {
		this.decreaseHealth(1);
    }
	// check if object has lived its minimum lifetime 
	public boolean hasLivedMinLifeTime() {
		double lifeTime = DurationHandler.getDurationSeconds(this.getSpawnTime());
		return lifeTime >= this.getMinLifeTime();
	}
	// check if game object is alive
	public boolean isAlive() {
		// check if health is valid OR if has lived for minimum amount of time has to live for
        return (this.getHealth() > 0 || !this.hasLivedMinLifeTime());
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

	// set random centre on x axis at top of screen
    protected void setRandomCentre() {
        // enemies will spawn randomly on x-axis off screen up
        int objectWidth = this.getWidth();
        int minX = objectWidth;
        int maxX = GameDisplay.getDisplayX() - objectWidth;
        int randomX = ThreadLocalRandom.current().nextInt(minX, maxX);
        this.setCentre(new Point3f(randomX, -this.getHeight(), 0));
    }

	// base collision method for ALL collision
	public boolean collide(GameObject other) {
		// used to make hitbox smaller for closer interaction
		float buffer = 4;
		// this object
		Point3f centre = this.getCentre();
		float width = this.getWidth();
		float widthBuffer = width/buffer;
		width = width - widthBuffer;
		float height = this.getHeight();
		float heightBuffer = height/buffer;
		height = height - heightBuffer;
		float thisX = centre.getX() + widthBuffer;
		float thisY = centre.getY() + heightBuffer;
		// other object
		Point3f otherCentre = other.getCentre();
		float otherWidth = other.getWidth();
		float otherWidthBuffer = width/buffer;
		otherWidth = otherWidth - otherWidthBuffer;
		float otherHeight = other.getHeight();
		float otherHeightBuffer = height/buffer;
		otherHeight = otherHeight - otherHeightBuffer;
		float otherX = otherCentre.getX() + otherWidthBuffer;
		float otherY = otherCentre.getY() + otherHeightBuffer;

		// // look at coords, to see best understandable coords bring player to top left, they will be THIS
		// System.out.println();
		// System.out.println(String.format("THIS = x:%f, y:%f, w:%f, h:%f", thisX, thisY, width, height));
		// System.out.println(String.format("THAT = x:%f, y:%f, w:%f, h:%f", otherX, otherY, otherWidth, otherHeight));

		// use AABB collision check -- return if true (suggested in slides, seen online and also suggested by ChatGPT)
		return 	thisX < otherX + otherWidth &&
				thisX + width > otherX &&
				thisY < otherY + otherHeight &&
				thisY + height > otherY;
	}

	// GameObject to string returns memory location but also location on screen
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