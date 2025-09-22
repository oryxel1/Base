package com.bascenario.engine.scenario.elements;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Builder
public final class Sound {
    private String path;
    @SerializedName("max-volume")
    private float maxVolume;
    @SerializedName("fade-in")
    private long fadeIn;
    @SerializedName("sound-id")
    private int soundId;

    public Sound(String path, float maxVolume, long fadeIn, int soundId) {
        this.path = path;
        this.maxVolume = maxVolume;
        this.fadeIn = fadeIn;
        this.soundId = soundId;
    }

    public String path() {
        return path;
    }

    public void path(String path) {
        this.path = path;
    }

    public float maxVolume() {
        return maxVolume;
    }

    public void maxVolume(float maxVolume) {
        this.maxVolume = maxVolume;
    }

    public long fadeIn() {
        return fadeIn;
    }

    public void fadeIn(long fadeIn) {
        this.fadeIn = fadeIn;
    }

    public int soundId() {
        return soundId;
    }

    public void soundId(int soundId) {
        this.soundId = soundId;
    }
}