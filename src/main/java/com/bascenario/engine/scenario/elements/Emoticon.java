package com.bascenario.engine.scenario.elements;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public final class Emoticon {
    private long duration;
    @SerializedName("offset-x")
    private float offsetX;
    @SerializedName("offset-y")
    private float offsetY;
    private EmoticonType type;
    @SerializedName("play-sound")
    private boolean playSound;

    public Emoticon(long duration, float offsetX, float offsetY, EmoticonType type) {
        this.duration = duration;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.type = type;
        this.playSound = true;
    }

    public Emoticon(long duration, float offsetX, float offsetY, EmoticonType type, boolean playSound) {
        this.duration = duration;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.type = type;
        this.playSound = playSound;
    }

    public long duration() {
        return this.duration;
    }

    public void duration(long duration) {
        this.duration = duration;
    }

    public float offsetX() {
        return this.offsetX;
    }

    public void offsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float offsetY() {
        return this.offsetY;
    }

    public void offsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public EmoticonType type() {
        return this.type;
    }

    public void type(EmoticonType type) {
        this.type = type;
    }

    public boolean playSound() {
        return this.playSound;
    }

    public void playSound(boolean playSound) {
        this.playSound = playSound;
    }

    @RequiredArgsConstructor
    @Getter
    public enum EmoticonType {
        NOTE("Note"), RESPOND("Respond"), ANGRY("Angry"), SWEAT("Sweat"), THINKING("Thinking"),
        HESITATED("Hesitated"), CHAT("Chat"), EXCLAMATION_MARK("Exclamation Mark"), HEART("Heart"), SURPRISED("Suprised"),
        QUESTION_MARK("Question Mark"), SHY("Shy"), ANXIETY("Anxiety"), TWINKLE("Twinkle");

        private final String name;

        public static String[] getAlls() {
            String[] strings = new String[EmoticonType.values().length];
            for (int i = 0; i < EmoticonType.values().length; i++) {
                strings[i] = EmoticonType.values()[i].name;
            }
            return strings;
        }
    }
}