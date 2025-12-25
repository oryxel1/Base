package oxy.bascenario.api.effects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Easing {
    LINEAR("Linear"), SINE("Sine"), QUAD("Quad"), CUBIC("Cubic"), QUART("Quart"), QUINT("Quint"), EXPO("Expo"), CIRC("Circ"), BACK("Back"), ELASTIC("Elastic"), BOUNCE("Bounce");

    private final String name;

    public static String[] getAlls() {
        String[] strings = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            strings[i] = values()[i].name;
        }
        return strings;
    }
}
