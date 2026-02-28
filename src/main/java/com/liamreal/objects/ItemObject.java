package com.liamreal.objects;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.liamreal.assets.AssetLoader;
import com.liamreal.assets.SoundPlayer;
import com.liamreal.enums.ItemType;

public class ItemObject extends GameObject {
    private ItemType itemType;
    // will have some data specific to item
    public ItemObject(ItemType itemType) {
        super(32, 32); // randomises centre
        this.itemType = itemType;
        this.setMinLifeTime(0.2);
        this.setHealth(1); // items die after being collided with by a player
        this.setTexture();
        this.setUseSound(String.format(
            "%s/sounds/items/%s/use.wav",
            AssetLoader.getAssetsPath(),
            this.getItemType()
        ));
	}
    public ItemObject() {
        super(32, 32); // randomises centre
        this.itemType = this.getRandomItemType();
        this.setMinLifeTime(0.2);
        this.setHealth(1); // items die after being collided with by a player
        this.setTexture();
        this.setUseSound(String.format(
            "%s/sounds/items/%s/use.wav",
            AssetLoader.getAssetsPath(),
            this.getItemType()
        ));
	}

    // get item type
    public ItemType getItemType() { return this.itemType; }

    // update texture
    public void updateTexture() { this.setTexture(); }

    protected ItemType getRandomItemType() {
        // randomly set item type based on those in enum
        List<ItemType> itemTypes = ItemType.getAllItemTypesExceptPortal();
        int randomItemIndex = ThreadLocalRandom.current().nextInt(0, itemTypes.size());
        itemType = itemTypes.get(randomItemIndex); // get random item from list and set as this item type
        return itemType;
    }
    
    // play item use sound
    public void playUseSound() {
        SoundPlayer.playSound(this.getUseSound());
    }
    
    // set texture specifically for item
    protected void setTexture() {
        ItemType itemType = this.getItemType();
        super.setTexture(String.format(
            "%s/textures/items/%s/%s.png", 
            AssetLoader.getAssetsPath(),
            itemType,
            itemType
        ));
    }
    
}
