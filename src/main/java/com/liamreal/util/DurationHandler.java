package com.liamreal.util;

import java.time.Duration;
import java.time.Instant;


// helps calculate cooldown times
public class DurationHandler {
    
    // find time (in seconds) since last cooldown application
    public static double getDurationSeconds(Instant startTime) {     
        Duration duration = Duration.between(startTime, Instant.now());
        double durationSeconds = duration.getSeconds() + duration.getNano() / 1000000000.0;
        return durationSeconds;
    }
}
