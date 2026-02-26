package com.liamreal.objects;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.liamreal.display.TextureLoader;
import com.liamreal.enums.ItemType;

public class ItemObject extends GameObject {
    private ItemType itemType;
    // will have some data specific to item
    public ItemObject() {
        super(32, 32); // randomises centre
        this.itemType = this.setRandomItemType(); // set item type for this item randomly on creation
        this.setMinLifeTime(0.2);
        this.setHealth(1); // items die after being collided with by a player
        this.setTexture();
	}

    // get item type
    public ItemType getItemType() { return this.itemType; }

    // update texture
    public void updateTexture() { this.setTexture(); }

    protected ItemType setRandomItemType() {
        // randomly set item type based on those in enum
        List<ItemType> itemTypes = ItemType.getAllItemTypes();
        int randomItemIndex = ThreadLocalRandom.current().nextInt(0, itemTypes.size());
        itemType = itemTypes.get(randomItemIndex); // get random item from list and set as this item type
        return itemType;
    }
    
    // set texture specifically for item
    protected void setTexture() {
        ItemType itemType = this.getItemType();
        super.setTexture(String.format(
            "%s/textures/items/%s/%s.png", 
            TextureLoader.getAssetsPath(),
            itemType,
            itemType
        ));
    }
    
}
