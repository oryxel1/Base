package com.bascenario.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
    public void play(String path) {
        Music music = Gdx.audio.newMusic(new FileHandle(path));
        music.play();

        this.nameToMusics.put(path, new CachedMusic(music));
    }

    public boolean fadeOut(String name) {
        final CachedMusic music = this.nameToMusics.get(name);
        if (music == null) {
            return true;
        }

        if (!music.fadingOut) {
            music.fadingOut = true;
            music.fadeOut.setTarget(0);
        }

        float volume = music.fadeOut.getValue();
        music.music.setVolume(volume);
        if (volume <= 0.001) {
            music.music.stop();
            this.nameToMusics.remove(name);
            return true;
        }

        return false;
    }

    public void stop(String name) {
        final CachedMusic music = this.nameToMusics.remove(name);
        if (music == null) {
            return;
        }

        music.music.stop();
    }

    public void loop(String name) {
        this.nameToMusics.get(name).music.setLooping(true);
    }

    @RequiredArgsConstructor
    @Setter
    public static class CachedMusic {
        private final Music music;
        private final DynamicAnimation fadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN, 1000L, 1);
        private boolean fadingOut;
    }
}
