package com.liamreal.time;

public class SystemTimeProvider implements TimeProvider {
    public long nanoTime() { return System.nanoTime(); }
    public long currentTimeMillis() { return System.currentTimeMillis(); }
}
