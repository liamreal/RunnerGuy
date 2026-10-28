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
    private final long DAMAGE_COOLDOWN_NANOS = TimeUnit.NANOSECONDS.convert(Health.DAMAGE_COOLDOWN_MILLISECONDS, TimeUnit.MILLISECONDS);

    @BeforeEach
    void setUp() {
        // will use this interface and mock what it has to return for each test
        timeProvider = mock(TimeProvider.class);
    }

    
    @Test
    void staticHealthAttributesNonNegative() {
        assertTrue(Health.SPAWN_COOLDOWN_MILLISECONDS >= 0);
        assertTrue(Health.DAMAGE_COOLDOWN_MILLISECONDS >= 0);
    }

    @Test
    void constructorSetsHealth() {
        // time provider specified for explicitness but unused in this test
        Health health = new Health(50, 100, timeProvider);

        assertEquals(50, health.getHealth());
    }

    @Test
    void singleArgumentConstructorStartsFullyHealed() {
        Health health = new Health(100, timeProvider);

        assertEquals(100, health.getHealth());
    }

    @Test
    void damageDoesNothingDuringSpawnCooldown() {
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS - 1  // damage() occurs before spawn cooldown ends
                );

        when(timeProvider.nanoTime())
                .thenReturn(0L);

        Health health = new Health(100, timeProvider);

        health.damage(25);

        // less than the spawn cooldown has passed
        assertEquals(100, health.getHealth());
    }

    @Test
    void damageDoesNothingDuringDamageCooldown() {
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS  // damage() occurs right as spawn cooldown ends
                );

        when(timeProvider.nanoTime())
                .thenReturn(
                        0L,  // constructor
                        0L - 1  // damage() occurs so that it occurs during the time which damage cooldown is active
                        // (because in constructor the damage cooldown is:      startTime - DAMAGE_COOLDOWN
                        //  so subtracting 1 puts us in that cooldown range)
                );

        Health health = new Health(100, timeProvider);

        health.damage(25);

        // spawn cooldown expired, damage cooldown still active
        assertEquals(100, health.getHealth());
    }

    @Test
    void damageReducesHealthAfterBothCooldowns() {
        when(timeProvider.currentTimeMillis())
                .thenReturn(
                        0L,  // constructor
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS  // damage() after spawn cooldown gone
                );

        when(timeProvider.nanoTime())
                .thenReturn(
                        0L,  // constructor
                        0L,  // damage() occurs outside of any damage cooldown
                        0L   // updateLastDamageTime() -- new previous damage time
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
                        0L,  // damage() outside of damage cooldown
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
                        0L + Health.SPAWN_COOLDOWN_MILLISECONDS   // second damage(), still after spawn cooldown
                );

        when(timeProvider.nanoTime())
                .thenReturn(
                        0L,  // constructor
                        0L,  // first damage()
                        0L,  // updateLastDamageTime()
                        0L + DAMAGE_COOLDOWN_NANOS - 1   // second damage() still within damage cooldown
                );

        Health health = new Health(100, timeProvider);

        health.damage(25);
        health.damage(25);

        assertEquals(75, health.getHealth());
    }

    @Test
    void damageCanBeAppliedAfterDamageCooldown() {
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
                        0L + DAMAGE_COOLDOWN_NANOS,  // second damage() -- after damage cooldown
                        0L + DAMAGE_COOLDOWN_NANOS   // updateLastDamageTime()
                );

        Health health = new Health(100, timeProvider);

        health.damage(25);
        health.damage(25);

        assertEquals(50, health.getHealth());
    }

    @Test
    void healIncreasesHealth() {
        Health health = new Health(50, 100, timeProvider);

        health.heal(25);

        assertEquals(75, health.getHealth());
    }

    @Test
    void healWillNotExceedMaxHealth() {
        when(timeProvider.currentTimeMillis()).thenReturn(0L);
        when(timeProvider.nanoTime()).thenReturn(0L);

        Health health = new Health(50, 100, timeProvider);

        health.heal(100);

        assertEquals(100, health.getHealth());
    }
}