package com.liamreal.display;

import com.liamreal.util.Vector2f;

public class GameDisplay {

	private static final int DISPLAY_X = 854;
	private static final int DISPLAY_Y = 540;
	private static final int SPRITE_WIDTH = 32;
	private static final int SPRITE_HEIGHT = 32;
	
	public static int getDisplayX() { return DISPLAY_X; }
	public static int getDisplayY() { return DISPLAY_Y; }
	public static int getSpriteWidth() { return SPRITE_WIDTH; }
	public static int getSpriteHeight() { return SPRITE_HEIGHT; }
	// get centre of screen (useful for placing player in centre of game)
	public static Vector2f getDisplayCentre() { return new Vector2f(getDisplayX()/2,getDisplayY()/2); }


	

}
