package oxy.bascenario.api.effects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransitionType {
    FADE("Fade"), FADE_WHITE("Fade White"),
    HORIZONTAL_SWIPE_LR("Horizontal Swipe Left To Right"),
    HORIZONTAL_SWIPE_RL("Horizontal Swipe Right To Left"),
    VERTICAL_SWIPE_BT("Vertical Swipe Bottom to Top"),
    VERTICAL_SWIPE_TB("Vertical Swipe Top to Bottom"),
    BACKGROUND_OVERLAP("Background Overlap");

    private final String name;

    public static String[] getAlls() {
        String[] strings = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            strings[i] = values()[i].name;
        }
        return strings;
    }
}
