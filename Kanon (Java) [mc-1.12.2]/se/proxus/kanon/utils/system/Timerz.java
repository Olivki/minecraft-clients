package se.proxus.kanon.utils.system;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Timerz {
    
    public static long getCurrentMilliseconds() {
        return getNanoTime() / 1000000;
    }
    
    public static long getNanoTime() {
        return System.nanoTime();
    }
}
