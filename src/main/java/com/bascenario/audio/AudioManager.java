package com.bascenario.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import net.lenni0451.commons.animation.DynamicAnimation;
import net.lenni0451.commons.animation.easing.EasingFunction;
import net.lenni0451.commons.animation.easing.EasingMode;

import java.util.HashMap;
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
    public void play(String path, boolean loop, boolean internal) {
        final Music music = Gdx.audio.newMusic(internal ? Gdx.files.internal(path) : new FileHandle(path));
        music.play();
        music.setLooping(loop);

        this.nameToMusics.put(path, new CachedMusic(music));
    }

    public void fadeIn(String path, long duration, boolean loop, boolean internal) {
        CachedMusic cachedMusic = this.nameToMusics.get(path);
        Music music;
        if (cachedMusic == null) {
            music = Gdx.audio.newMusic(internal ? Gdx.files.internal(path) : new FileHandle(path));
            music.setVolume(0);
            music.setLooping(loop);
            music.play();
            cachedMusic = new CachedMusic(music);

            cachedMusic.fadeIn = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN, duration, 0);
            cachedMusic.fadeIn.setTarget(1);

            this.nameToMusics.put(path, cachedMusic);
        } else {
            music = cachedMusic.music;
        }

        System.out.println("Fade in: " + cachedMusic.fadeIn.getValue());
        if (cachedMusic.fadeIn.isRunning()) {
            music.setVolume(cachedMusic.fadeIn.getValue());
        } else {
            if (music.getVolume() != 1) {
                music.setVolume(1);
            }
        }
    }

    public void fadeOut(String name, long duration) {
        final CachedMusic music = this.nameToMusics.get(name);
        if (music == null) {
            return;
        }

        if (music.fadeOut == null) {
            music.fadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_OUT, duration, 1);
            music.fadeOut.setTarget(0);
        }

        float volume = music.fadeOut.getValue();

        music.music.setVolume(volume);
        if (volume <= 0.001) {
            music.music.stop();
            this.nameToMusics.remove(name);
        }
    }

    public void stop(String name) {
        final CachedMusic music = this.nameToMusics.remove(name);
        if (music == null) {
            return;
        }

        music.music.stop();
    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class CachedMusic {
        private final Music music;
        private DynamicAnimation fadeOut, fadeIn;
    }
}
