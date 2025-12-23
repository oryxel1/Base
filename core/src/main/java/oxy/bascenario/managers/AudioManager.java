package oxy.bascenario.managers;

import lombok.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.utils.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.utils.FileUtils;
import oxy.bascenario.utils.animation.AnimationUtils;

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

    public void play(Sound sound, long fadeIn) {
        play(null, sound, fadeIn);
    }

    public void play(Scenario scenario, Sound sound, long fadeIn) {
        final Music music = Gdx.audio.newMusic(FileUtils.toHandle(scenario, sound.file()));
        music.play();

        boolean fade = fadeIn > 0;

        music.setVolume(fade ? 0 : sound.maxVolume());
        music.setLooping(sound.loop());

        final CachedSound cache = new CachedSound(music, sound);
        if (fade) {
            cache.fadeIn = AnimationUtils.build(fadeIn, 0, sound.maxVolume(), EasingFunction.LINEAR);
        }

        this.cachedSounds.put(sound.id(), cache);
    }

    public void fade(int id, long duration, float volume, Easing easing) {
        if (duration <= 0) {
            return;
        }

        final CachedSound cache = this.cachedSounds.get(id);
        if (cache != null) {
            cache.fade = AnimationUtils.build(duration, 0, Math.abs(volume), AnimationUtils.toFunction(easing));
        }
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

    public void stop(int id, int fadeDuration) {
        final CachedSound cache = this.cachedSounds.get(id);
        if (cache == null) {
            return;
        }
        boolean fade = fadeDuration > 0;
        if (fade) {
            cache.fadeOut = AnimationUtils.build(fadeDuration, cache.gdxMusic.getVolume(), 0, EasingFunction.LINEAR);
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
            } else if (cache.fade != null) {
                if (cache.fade.isRunning()) {
                    cache.gdxMusic.setVolume(cache.fade.getValue());
                } else {
                    cache.gdxMusic.setVolume(cache.fade.getTarget());
                    cache.fade = null;
                }
            }
        }
    }

    @RequiredArgsConstructor
    private static class CachedSound {
        private final Music gdxMusic;
        private final Sound sound;
        private DynamicAnimation fadeOut, fadeIn;
        private DynamicAnimation fade;
    }
}