package com.liamreal.logic.item;

import com.liamreal.enums.Direction;
import com.liamreal.logic.object.ObjectLogic;
import com.liamreal.user.Config;
import com.liamreal.objects.ItemObject;

public class ItemLogic extends ObjectLogic {
    // specify item speed
    public ItemLogic(ItemObject itemObject, int moveSpeed) {
        super(itemObject, moveSpeed);
    }
    public ItemLogic(ItemObject itemObject) {
        // by default item has config speed (same as used by enemies)
        this(itemObject, Config.getInstance().getGeneralMoveSpeed());
    }

    // move item down
    public void move() {
        super.move(Direction.DOWN);
    }

}
