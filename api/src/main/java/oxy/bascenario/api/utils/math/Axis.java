package oxy.bascenario.api.utils.math;

public enum Axis {
    X, Y;

    public static String[] getAlls() {
        String[] strings = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            strings[i] = "Axis " + values()[i].name();
        }
        return strings;
    }
}
