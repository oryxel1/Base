package oxy.bascenario.managers;

import lombok.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.utils.FileUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AudioManager {
    @Getter
    private static final AudioManager instance = new AudioManager();
    private AudioManager() {
        if (instance != null) {
            throw new RuntimeException("This class can only create one instance!");
        }
    }

    private final Map<Integer, CachedSound> cachedSounds = new HashMap<>();

    public void play(Sound sound) {
        final Music music = Gdx.audio.newMusic(FileUtils.toHandle(sound.file()));
        music.play();

        boolean fade = sound.fadeIn() > 0;

        music.setVolume(fade ? 0 : sound.maxVolume());
        music.setLooping(sound.loop());

        final CachedSound cache = new CachedSound(music, sound);
        if (fade) {
            cache.fadeIn = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN, sound.fadeIn(), 0);
            cache.fadeIn.setTarget(sound.maxVolume());
        }

        this.cachedSounds.put(sound.id(), cache);
    }

    public void pause(int id) {
        final CachedSound cache = this.cachedSounds.get(id);
        if (cache != null) {
            cache.gdxMusic.pause();
        }
    }

    public void resume(int id) {
        final CachedSound cache = this.cachedSounds.get(id);
        if (cache != null) {
            cache.gdxMusic.play();
        }
    }

    public void stop(int id) {
        final CachedSound cache = this.cachedSounds.get(id);
        if (cache == null) {
            return;
        }
        boolean fade = cache.sound.fadeOut() > 0;
        if (fade) {
            cache.fadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_OUT, cache.sound.fadeOut(), cache.gdxMusic.getVolume());
            cache.fadeOut.setTarget(0);
        } else {
            cache.gdxMusic.stop();
            this.cachedSounds.remove(id);
        }
    }

    public void tick() {
        final Iterator<Map.Entry<Integer, CachedSound>> iterator = this.cachedSounds.entrySet().iterator();
        while (iterator.hasNext()) {
            final CachedSound cache = iterator.next().getValue();
            if (cache == null) {
                continue;
            }

            if (cache.fadeOut != null) {
                if (cache.fadeOut.isRunning()) {
                    cache.gdxMusic.setVolume(cache.fadeOut.getValue());
                } else {
                    cache.gdxMusic.stop();
                    iterator.remove();
                }
            } else if (cache.fadeIn != null) {
                if (cache.fadeIn.isRunning()) {
                    cache.gdxMusic.setVolume(cache.fadeIn.getValue());
                } else {
                    cache.gdxMusic.setVolume(cache.fadeIn.getTarget());
                    cache.fadeIn = null;
                }
            }
        }
    }

    @RequiredArgsConstructor
    private class CachedSound {
        private final Music gdxMusic;
        private final Sound sound;
        private DynamicAnimation fadeOut, fadeIn;
    }
}