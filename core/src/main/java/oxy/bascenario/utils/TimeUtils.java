package oxy.bascenario.utils;

public class TimeUtils {
    public static Long fakeTimeMillis;
    
    public static long currentTimeMillis() {
        if (fakeTimeMillis != null) {
            return fakeTimeMillis;
        }
        
        return System.currentTimeMillis();
    }
}
