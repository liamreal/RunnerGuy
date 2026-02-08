package util;

import java.time.Duration;
import java.time.Instant;

import display.GameDisplay;
import util.GameObject;
import util.Point3f;

public class CooldownHandler {
    
    // find time (in seconds) since last cooldown application
    public static double findTimeSinceLastCooldown(Instant cooldownStartTime) {     
        Duration durationSinceLastCooldown = Duration.between(cooldownStartTime, Instant.now());
        double secondsSinceLastCooldown = durationSinceLastCooldown.getSeconds() + durationSinceLastCooldown.getNano() / 1000000000.0;
        return secondsSinceLastCooldown;
    }

    public static boolean isOnCooldown(Instant lastCooldownStart, double cooldownLength) {
        // if (findTimeSinceLastCooldown(lastCooldownStart) <= cooldownLength) {
        //     System.out.println("less or equal...");
        // }
        // else {
        //     System.out.println("MORE");
        // }
        return findTimeSinceLastCooldown(lastCooldownStart) <= cooldownLength;
    }

    
    // reset start time of cooldown (used for enemy spawns and later bullet spawns too)
    public static void resetCooldown(Instant lastCooldownStart) {
        lastCooldownStart = Instant.now();
    }
}
