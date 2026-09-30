package com.liamreal.components.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DamageTest {

    @Test
    void constructorSetsDamage() {
        Damage damage = new Damage(25);

        assertEquals(25, damage.getDamage());
    }

    @Test
    void setDamageChangesDamage() {
        Damage damage = new Damage(25);

        damage.setDamage(50);

        assertEquals(50, damage.getDamage());
    }

    @Test
    void setDamageCanSetDamageToZero() {
        Damage damage = new Damage(25);

        damage.setDamage(0);

        assertEquals(0, damage.getDamage());
    }

    @Test
    void setDamageCanSetNegativeDamage() {
        Damage damage = new Damage(25);

        damage.setDamage(-10);

        assertEquals(0, damage.getDamage());
    }
}