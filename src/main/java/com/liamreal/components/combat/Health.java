package com.liamreal.components.combat;

import java.util.concurrent.TimeUnit;

import com.liamreal.ecs.Component;
import com.liamreal.time.SystemTimeProvider;
import com.liamreal.time.TimeProvider;

public class Health implements Component {
    // cooldowns for spawn/damage (1000ms = 1s)
    public static final long SPAWN_COOLDOWN_MILLISECONDS = 250;
    public static final long DAMAGE_COOLDOWN_MILLISECONDS = 1000;
    private TimeProvider timeProvider;
    private int health;
    private int maxHealth;
    private long lastDamageTimeNanos;
    private long spawnTimeMillis;

    public Health(int health, int maxHealth, TimeProvider timeProvider) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.timeProvider = timeProvider;
        this.initialiseStartTimes();    
    }
    // automatically use System for time calls
    public Health(int health, int maxHealth) { this(health, maxHealth, new SystemTimeProvider()); }

    // automatically creates health component fully healed
    public Health(int maxHealth) { this(maxHealth, maxHealth); }
    public Health(int maxHealth, TimeProvider timeProvider) { this(maxHealth, maxHealth, timeProvider); }

    void initialiseStartTimes() { 
        this.spawnTimeMillis = timeProvider.currentTimeMillis();
        // if just spawned cooldown is set so it does not apply to first spawn
        this.lastDamageTimeNanos = timeProvider.nanoTime() - TimeUnit.MILLISECONDS.toNanos(DAMAGE_COOLDOWN_MILLISECONDS);
    }
    long getSpawnTime() { return this.spawnTimeMillis; }

    // getters/setters
    public int getHealth() { return this.health; }

    // need to keep track of when last damaged for cooldown
    private void updateLastDamageTime() { this.lastDamageTimeNanos = timeProvider.nanoTime(); }
    // calculates time since spawn
    private long getTimeSinceSpawnMilliseconds() {
        return TimeUnit.MILLISECONDS.convert(timeProvider.currentTimeMillis() - this.getSpawnTime(), TimeUnit.MILLISECONDS);
    }
    // calculates elapsed time in seconds since last time was damaged
    private long getTimeSinceLastDamageMilliseconds() {
        return TimeUnit.MILLISECONDS.convert(timeProvider.nanoTime() - this.lastDamageTimeNanos, TimeUnit.NANOSECONDS);
    }

    // --------------------
    // -- health methods --
    // --------------------
    
    public void damage(int damage) { 
        // no damage if just spawned
        if (this.getTimeSinceSpawnMilliseconds() < SPAWN_COOLDOWN_MILLISECONDS) { return; }
        // no damage if cooldown not complete
        if (this.getTimeSinceLastDamageMilliseconds() < DAMAGE_COOLDOWN_MILLISECONDS) { return; }
        // cannot have negative health
        this.health = Math.max(this.health - damage, 0);
        this.updateLastDamageTime();
    }
    // cannot exceed max health
    public void heal(int healing) { this.health = Math.min(this.health + healing, this.maxHealth); }
}
