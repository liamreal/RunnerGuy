package com.liamreal.components.combat;

import com.liamreal.ecs.Component;

public class Health implements Component {
    private int health;
    private int maxHealth;

    public Health(int health, int maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
    }

    // automatically creates health component fully healed
    public Health(int maxHealth) { this(maxHealth, maxHealth); }

    // getters/setters
    public int getHealth() { return this.health; }

    // --------------------
    // -- health methods --
    // --------------------
    
    public void damage(int damage) { this.health -= damage; }
    // cannot exceed max health
    public void heal(int healing) { this.health = Math.min(this.health + healing, this.maxHealth); }
}
