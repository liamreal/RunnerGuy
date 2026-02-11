package util;

import java.time.Instant;

// helps calculate cooldown times
public class CooldownHandler {
    
    // find time (in seconds) since last cooldown application
    public static double findTimeSinceLastCooldown(Instant cooldownStartTime) {     
        return DurationHandler.getDurationSeconds(cooldownStartTime);
    }

    public static boolean isOnCooldown(Instant lastCooldownStart, double cooldownLength) {
        return findTimeSinceLastCooldown(lastCooldownStart) <= cooldownLength;
    }
}
