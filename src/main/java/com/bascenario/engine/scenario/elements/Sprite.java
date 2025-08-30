package com.bascenario.engine.scenario.elements;

import lombok.Builder;

@Builder
public record Sprite(String skeleton, String atlas, String defaultAnimation, boolean fadeIn) {
    public record Emoticon(long duration, long appearDuration, float offsetX, float offsetY, EmoticonType type) {
    }

    public enum EmoticonType {
        SWEAT, THINKING, CHAT, EXCLAMATION, EXCLAMATION_MARK, HEART,
        QUESTION, QUESTION_MARK, SHY, ANXIETY, TWINKLE
    }
}