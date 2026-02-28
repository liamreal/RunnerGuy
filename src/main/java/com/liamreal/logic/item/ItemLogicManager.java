package com.liamreal.logic.item;

import java.util.concurrent.CopyOnWriteArrayList;
import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.logic.object.ObjectLogicManager;
import com.liamreal.objects.ItemObject;
import com.liamreal.user.Config;
import com.liamreal.enums.Direction;
import com.liamreal.enums.ItemType;

// generic method to manage different logics (will manage for example item logic thru a subclass)
public class ItemLogicManager extends ObjectLogicManager {
    // objects list (in subclasses will have a getter which is respective to item managing)
	private CopyOnWriteArrayList<ObjectLogic> enemies = super.getObjects();
    private int defaultItemHealth = super.getDefaultObjectHealth();

    public ItemLogicManager() {
        super();
        // by default enemies move down
        this.setDefaultMoveDirection(Direction.DOWN);
        // set max num enemies based on config
        this.setMaxNumItems(Config.getInstance().getMaxNumItems());
        // // start with 2 basic enemies
        // enemies.add(new ItemLogic(new ItemObject(itemType)));
        // enemies.add(new ItemLogic(new ItemObject(itemType)));
    }

    // getters and setters
    public int getMaxNumItems() { return super.getMaxNumObjects(); }
    public int getDefaultItemHealth() { return this.defaultItemHealth; }
    public void setDefaultItemHealth(int newItemHealth) { this.defaultItemHealth = newItemHealth; }
    public void setMaxNumItems(int newMaxNumItems) { 
        super.setMaxNumObjects(newMaxNumItems);
    }



    // by default move enemies down
    public void moveItems() {
        this.moveItems(this.getDefaultMoveDirection());
    }
    // move every item in list
    public void moveItems(Direction direction) {
		super.moveObjects(direction);
    }



    public boolean spawnItemAttempt() {
        ItemLogic newItem = new ItemLogic(new ItemObject());
        return super.spawnObjectAttempt(newItem);
    }
    public boolean spawnPortalItemAttempt() {
        ItemLogic newItem = new ItemLogic(new ItemObject(ItemType.PORTAL));
        return super.spawnObjectAttempt(newItem);
    }


    // obtain list of objects being managed
    public CopyOnWriteArrayList<ObjectLogic> getItems() {
        return this.enemies;
    }
}
