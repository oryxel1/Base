package oxy.base.api.effects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScreenEffect {
    GRAY_FILTER("Gray Filter"),
    SMOKE("Smoke"),
    SHINING("Shining"),
    SHINING_NO_BG("Shining No Background"),
    FILM_GRAIN("Film Grain"),
    FILM_GRAIN_NO_TONE("Film Grain No Tone"),
    BLACK_AND_WHITE("Black And White"),
    NIGHT_VISION("Night Vision"),
    TV_NOISE("Tv Noise")
    ;

    private final String name;

    public static String[] getAlls() {
        String[] strings = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            strings[i] = values()[i].name;
        }
        return strings;
    }
}
