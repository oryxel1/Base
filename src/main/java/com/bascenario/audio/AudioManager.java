package com.bascenario.audio;

import com.bascenario.audio.decoder.OggAudioInputStream;
import lombok.Getter;
import lombok.SneakyThrows;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileInputStream;

public class AudioManager {
    @Getter
    private static final AudioManager instance = new AudioManager();
    private AudioManager() {
        if (instance != null) {
            throw new RuntimeException("This class can only create one instance!");
        }
    }

    private Clip clip;

    @SneakyThrows
    public void play(File file) {
        if (this.clip != null) {
            this.clip.stop();
        }

        this.clip = AudioSystem.getClip();
        this.clip.open(OggAudioInputStream.create(new FileInputStream(file)));
        this.clip.start();
    }

    public void stop() {
        this.clip.stop();
        this.clip = null;
    }

    public void loop() {
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
