package util;

import java.time.Duration;
import java.time.Instant;

import display.GameDisplay;
import util.GameObject;
import util.Point3f;

// helps calculate cooldown times
public class CooldownHandler {
    
    // find time (in seconds) since last cooldown application
    public static double findTimeSinceLastCooldown(Instant cooldownStartTime) {     
        Duration durationSinceLastCooldown = Duration.between(cooldownStartTime, Instant.now());
        double secondsSinceLastCooldown = durationSinceLastCooldown.getSeconds() + durationSinceLastCooldown.getNano() / 1000000000.0;
        return secondsSinceLastCooldown;
    }

    public static boolean isOnCooldown(Instant lastCooldownStart, double cooldownLength) {
        return findTimeSinceLastCooldown(lastCooldownStart) <= cooldownLength;
    }
}
