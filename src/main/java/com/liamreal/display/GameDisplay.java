package com.liamreal.display;

import com.liamreal.util.Vector2f;

public class GameDisplay {

	private static final int DISPLAY_X = 854;
	private static final int DISPLAY_Y = 540;
	private static final int SPRITE_WIDTH = 32;
	private static final int SPRITE_HEIGHT = 32;
	private static final int TARGET_UPS = 60;
	private static final double TIME_PER_TICK = 1000000000.0 / TARGET_UPS;
	
	public static int getDisplayX() { return DISPLAY_X; }
	public static int getDisplayY() { return DISPLAY_Y; }
	public static int getSpriteWidth() { return SPRITE_WIDTH; }
	public static int getSpriteHeight() { return SPRITE_HEIGHT; }
	public static double getTimePerTick() { return TIME_PER_TICK; }
	// get centre of screen (useful for placing player in centre of game)
	public static Vector2f getDisplayCentre() { return new Vector2f(getDisplayX()/2,getDisplayY()/2); }


	

}
