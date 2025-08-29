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
    public void play(String path, boolean loop) {
        long id = -1;
        final Object object;
        if (path.contains(".wav")) {
            object = Gdx.audio.newSound(new FileHandle(path));
            id = ((Sound)object).play();
            if (loop) {
                ((Sound)object).loop();
            }
        } else {
            object = Gdx.audio.newMusic(new FileHandle(path));
            ((Music)object).play();
            if (loop) {
                ((Music)object).setLooping(true);
            }
        }

        this.nameToMusics.put(path, new CachedMusic(id, object));
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

        if (music.music instanceof Sound sound) {
            sound.setVolume(music.id, volume);
        } else if (music.music instanceof Music music1) {
            music1.setVolume(volume);
        }
        if (volume <= 0.001) {
            if (music.music instanceof Sound sound) {
                sound.stop(music.id);
            } else if (music.music instanceof Music music1) {
                music1.stop();
            }
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

        if (music.music instanceof Sound sound) {
            sound.stop(music.id);
        } else if (music.music instanceof Music music1) {
            music1.stop();
        }
    }

    @RequiredArgsConstructor
    @Setter
    public static class CachedMusic {
        private final long id;
        private final Object music;
        private final DynamicAnimation fadeOut = new DynamicAnimation(EasingFunction.LINEAR, EasingMode.EASE_IN, 1000L, 1);
        private boolean fadingOut;
    }
}
