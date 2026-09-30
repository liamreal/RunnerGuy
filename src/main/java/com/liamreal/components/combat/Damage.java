package com.liamreal.components.combat;

import com.liamreal.ecs.Component;

public class Damage implements Component {
    private int damage;

    public Damage(int damage) {
        this.setDamage(damage);
    }
    
    // getters/setters
    public int getDamage() { return this.damage; }
    public void setDamage(int newDamage) { this.damage = Math.max(newDamage, 0); } // do not allow negative damage
}
