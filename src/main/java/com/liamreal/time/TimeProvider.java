package com.liamreal.time;

// used to provide time, so can be used for real System time or for mocking time (for tests)
public interface TimeProvider {
    long nanoTime();
    long currentTimeMillis();
}
