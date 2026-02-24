package com.liamreal.objects;

import com.liamreal.display.TextureLoader;
import com.liamreal.enums.PlayerType;
import com.liamreal.util.Point3f;

public class PlayerObject extends GameObject {
    private final PlayerType playerType;
    // will have some data specific to player and inherit superclass methods
    public PlayerObject(PlayerType playerType) {  
        super();
        this.playerType = playerType;
        this.setTexture();
	}
    public PlayerObject(PlayerType playerType, String textureLocation, int width,int height,Point3f centre) { 
        super(textureLocation, width, height, centre);
        this.playerType = playerType;
	}
    // auto-build texture based on file path pre-specified field (the way i will use for base folder structure)
    public PlayerObject(PlayerType playerType, int width,int height,Point3f centre) { 
        super(width, height, centre);
        this.playerType = playerType;
        // set player texture
        this.setTexture();
	}

    // a way to get player type
    public PlayerType getPlayerType() { return this.playerType; }

    // if no argument defaults to player one texture
    protected void setTexture() {
        this.setTexture(playerType);
    }
    // set texture specifically for player
    protected void setTexture(PlayerType playerType) {
        super.setTexture(String.format(
            "%s/textures/players/player_%s.png", 
            TextureLoader.getAssetsPath(),
            playerType.toString()
        ));
    }
}
