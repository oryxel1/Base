package oxy.bascenario.engine;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import oxy.bascenario.engine.base.Sound;

import java.util.HashMap;
import java.util.Map;

public final class SoundManager {
    @Getter
    private static final SoundManager instance = new SoundManager();
    private SoundManager() {
        if (instance != null) {
            throw new RuntimeException("This class can only create one instance!");
        }
    }

    private final Map<Integer, CachedSound> sounds = new HashMap<>();

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class CachedSound {
        private final Sound music;
        private DynamicAnimation fadeOut, fadeIn;
    }
}
