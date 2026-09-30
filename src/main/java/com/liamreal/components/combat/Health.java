package com.liamreal.components.combat;

import java.util.concurrent.TimeUnit;

import com.liamreal.ecs.Component;

public class Health implements Component {
    // global hit cooldown for all entities with health component
    private static final int DAMAGE_COOLDOWN_SECONDS = 1;
    private int health;
    private int maxHealth;
    private long lastDamageTime;

    public Health(int health, int maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.updateLastDamageTime();
    }

    // automatically creates health component fully healed
    public Health(int maxHealth) { this(maxHealth, maxHealth); }

    // getters/setters
    public int getHealth() { return this.health; }

    // need to keep track of when last damaged for cooldown
    private void updateLastDamageTime() { this.lastDamageTime = System.nanoTime(); }
    // calculates elapsed time in seconds since last time was damaged
    private long getTimeSinceLastDamageSeconds() { return TimeUnit.SECONDS.convert(System.nanoTime() - this.lastDamageTime, TimeUnit.NANOSECONDS); }

    // --------------------
    // -- health methods --
    // --------------------
    
    public void damage(int damage) { 
        // no damage if cooldown not complete
        if (this.getTimeSinceLastDamageSeconds() < DAMAGE_COOLDOWN_SECONDS) { return; }
        // cannot have negative health
        this.health = Math.max(this.health - damage, 0);
        this.updateLastDamageTime();
    }
    // cannot exceed max health
    public void heal(int healing) { this.health = Math.min(this.health + healing, this.maxHealth); }
}
