package com.bascenario.engine.scenario.elements;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public record Sprite(String skeleton, String atlas, @SerializedName("default-animation") String defaultAnimation, @SerializedName("fade-in") boolean fadeIn) {
    public static final class Emoticon {
        private final long duration;
        @SerializedName("offset-x")
        private final float offsetX;
        @SerializedName("offset-y")
        private final float offsetY;
        private final EmoticonType type;
        @SerializedName("play-sound")
        private final boolean playSound;

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
            return duration;
        }

        public float offsetX() {
            return offsetX;
        }

        public float offsetY() {
            return offsetY;
        }

        public EmoticonType type() {
            return type;
        }

        public boolean playSound() {
            return playSound;
        }
    }

    public enum EmoticonType {
        NOTE, RESPOND, ANGRY, SWEAT, THINKING, HESITATED, CHAT, EXCLAMATION_MARK, HEART, SURPRISED,
        QUESTION_MARK, SHY, ANXIETY, TWINKLE
    }
}