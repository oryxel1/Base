package oxy.bascenario.api.animation.api;

import java.util.List;

public interface Animation {
    void render();
    long duration();
    String type();

    default List<AnimationOption<?>> options() {
        return List.of();
    }
    default <T> T get(int index) {
        return null;
    }
    default void set(int index, Object value) {
    }
}
