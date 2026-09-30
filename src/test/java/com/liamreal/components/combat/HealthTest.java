package com.liamreal.components.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liamreal.time.TimeProvider;

class HealthTest {
    private TimeProvider timeProvider;

    @BeforeEach
    void setUp() {
        timeProvider = mock(TimeProvider.class);
    }
    
    @Test
    void staticHealthAttributesNonNegative() {
        assertTrue(Health.SPAWN_COOLDOWN_MILLISECONDS >= 0);
        assertTrue(Health.DAMAGE_COOLDOWN_MILLISECONDS >= 0);
    }

    @Test
    void constructorSetsHealth() {
        when(timeProvider.currentTimeMillis()).thenReturn(0L);
        when(timeProvider.nanoTime()).thenReturn(0L);

        Health health = new Health(50, 100);

        assertEquals(50, health.getHealth());
    }

    @Test
    void singleArgumentConstructorStartsFullyHealed() {
        when(timeProvider.currentTimeMillis()).thenReturn(0L);
        when(timeProvider.nanoTime()).thenReturn(0L);

        Health health = new Health(100, timeProvider);

        assertEquals(100, health.getHealth());
    }

    @Test
    void damageDoesNothingDuringSpawnCooldown() {
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS - 1  // damage()
                );

        when(timeProvider.nanoTime())
                .thenReturn(0L);

        Health health = new Health(100, timeProvider);

        health.damage(25);

        // Less than the spawn cooldown has passed.
        assertEquals(100, health.getHealth());
    }

    @Test
    void damageDoesNothingDuringDamageCooldown() {
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS  // damage()
                );

        when(timeProvider.nanoTime())
                .thenReturn(
                        0L,  // constructor
                        0L - 1  // damage() -- because in constructor the damage cooldown is startTime - DAMAGE_COOLDOWN
                );

        Health health = new Health(100, timeProvider);

        health.damage(25);

        // Spawn cooldown has expired, but damage cooldown has not.
        assertEquals(100, health.getHealth());
    }

    @Test
    void damageReducesHealthAfterBothCooldowns() {
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS  // damage()
                );

        when(timeProvider.nanoTime())
                .thenReturn(
                        0L,  // constructor
                        0L,  // damage() -- because when an entity spawns can be damaged, only thing stopping it is SPAWN_COOLDOWN, not here tho
                        0L   // updateLastDamageTime()
                );

        Health health = new Health(100, timeProvider);

        health.damage(25);

        assertEquals(75, health.getHealth());
    }

    @Test
    void damageCannotReduceHealthBelowZero() {
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS  // damage()
                );

        when(timeProvider.nanoTime())
                .thenReturn(
                        0L,  // constructor
                        0L,  // damage()
                        0L   // updateLastDamageTime()
                );

        Health health = new Health(50, timeProvider);

        health.damage(100);

        assertEquals(0, health.getHealth());
    }

    @Test
    void secondDamageDuringDamageCooldownIsIgnored() {
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS,  // first damage()
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS   // second damage()
                );

        when(timeProvider.nanoTime())
                .thenReturn(
                        0L,  // constructor
                        0L,  // first damage()
                        0L,  // updateLastDamageTime()
                        0L + 1  // second damage()
                );

        Health health = new Health(100, timeProvider);

        health.damage(25);
        health.damage(25);

        assertEquals(75, health.getHealth());
    }

    @Test
    void damageCanBeAppliedAfterDamageCooldown() {
        long damageCooldownNanos = TimeUnit.NANOSECONDS.convert(Health.DAMAGE_COOLDOWN_MILLISECONDS, TimeUnit.MILLISECONDS);
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS,  // first damage()
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS   // second damage()
                );

        when(timeProvider.nanoTime())
                .thenReturn(
                        0L,  // constructor
                        0L,  // first damage()
                        0L,  // updateLastDamageTime()
                        0L + damageCooldownNanos,  // second damage()
                        0L + damageCooldownNanos   // updateLastDamageTime()
                );

        Health health = new Health(100, timeProvider);

        health.damage(25);
        assertEquals(75, health.getHealth());
        health.damage(25);
        assertEquals(50, health.getHealth());
    }

    @Test
    void healIncreasesHealth() {
        when(timeProvider.currentTimeMillis()).thenReturn(0L);
        when(timeProvider.nanoTime()).thenReturn(0L);

        Health health = new Health(50, 100, timeProvider);

        health.heal(25);

        assertEquals(75, health.getHealth());
    }

    @Test
    void healCannotExceedMaxHealth() {
        when(timeProvider.currentTimeMillis()).thenReturn(0L);
        when(timeProvider.nanoTime()).thenReturn(0L);

        Health health = new Health(50, 100, timeProvider);

        health.heal(100);

        assertEquals(100, health.getHealth());
    }

    @Test
    void healCanRestoreHealthToMax() {
        when(timeProvider.currentTimeMillis()).thenReturn(0L);
        when(timeProvider.nanoTime()).thenReturn(0L);

        Health health = new Health(25, 100, timeProvider);

        health.heal(75);

        assertEquals(100, health.getHealth());
    }

}