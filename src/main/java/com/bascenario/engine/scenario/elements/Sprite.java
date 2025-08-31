package com.bascenario.engine.scenario.elements;

import lombok.Builder;

@Builder
public record Sprite(String skeleton, String atlas, String defaultAnimation, boolean fadeIn) {
    public static final class Emoticon {
        private final long duration;
        private final float offsetX;
        private final float offsetY;
        private final EmoticonType type;
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
        SWEAT, THINKING, HESITATED, CHAT, EXCLAMATION_MARK, HEART, EXCLAMATION_QUESTION,
        QUESTION_MARK, SHY, ANXIETY, TWINKLE
    }
}