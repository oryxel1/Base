package com.bascenario.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.bascenario.util.MathUtil;
import lombok.*;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

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

    private final Map<String, CachedMusic> nameToMusics = new HashMap<>();

    @SneakyThrows
    public void play(String path, boolean loop, float maxVolume, boolean internal) {
        final Music music = Gdx.audio.newMusic(internal ? Gdx.files.internal(path) : new FileHandle(path));
        music.play();
        music.setVolume(Math.abs(maxVolume));
        music.setLooping(loop);

        this.nameToMusics.put(path, new CachedMusic(music));
    }

    public void playFadeIn(String path, long fadeDuration, boolean loop, float maxVolume, boolean internal) {
        final Music music = Gdx.audio.newMusic(internal ? Gdx.files.internal(path) : new FileHandle(path));
        music.play();
        music.setVolume(0);
        music.setLooping(loop);

        final CachedMusic cachedMusic = new CachedMusic(music);
        this.nameToMusics.put(path, cachedMusic);

        cachedMusic.fadeIn = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN, fadeDuration, 0);
        cachedMusic.fadeIn.setTarget(Math.abs(maxVolume));
    }

    public void stop(String name) {
        final CachedMusic music = this.nameToMusics.remove(name);
        if (music == null) {
            return;
        }

        music.music.stop();
    }

    public void stopFadeOut(String name, long fadeDuration) {
        final CachedMusic music = this.nameToMusics.get(name);
        if (music == null) {
            return;
        }

        music.fadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_OUT, fadeDuration, music.music.getVolume());
        music.fadeOut.setTarget(0);
    }

    public void tickFadeIn() {
        for (Map.Entry<String, CachedMusic> entry : this.nameToMusics.entrySet()) {
            final CachedMusic music = entry.getValue();
            if (music == null || music.fadeIn == null) {
                continue;
            }

            if (music.fadeIn.isRunning()) {
                music.music.setVolume(music.fadeIn.getValue());
            } else {
                if (music.music.getVolume() != music.fadeIn.getTarget()) {
                    music.music.setVolume(music.fadeIn.getTarget());
                }

                music.fadeIn = null;
            }
        }
    }

    public void tickFadeOut() {
        final Iterator<Map.Entry<String, CachedMusic>> iterator = this.nameToMusics.entrySet().iterator();
        while (iterator.hasNext()) {
            final CachedMusic music = iterator.next().getValue();
            if (music == null || music.fadeOut == null) {
                continue;
            }

            if (music.fadeOut.isRunning()) {
                music.music.setVolume(music.fadeOut.getValue());
            } else {
                music.music.stop();
                iterator.remove();
            }
        }
    }

    @RequiredArgsConstructor
    @ToString
    @Getter
    @Setter
    public static class CachedMusic {
        private final Music music;
        private DynamicAnimation fadeOut, fadeIn;
    }
}
