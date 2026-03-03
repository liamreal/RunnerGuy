package com.liamreal.display;

import com.liamreal.util.Point3f;

public class GameDisplay {

	private static final int DISPLAY_X = 854;
	private static final int DISPLAY_Y = 480;
	
	public static int getDisplayX() { return DISPLAY_X; }
	public static int getDisplayY() { return DISPLAY_Y; }
	// get centre of screen (useful for placing player in centre of game)
	public static Point3f getDisplayCentre() { return new Point3f(getDisplayX()/2,getDisplayY()/2,0); }


	

}
