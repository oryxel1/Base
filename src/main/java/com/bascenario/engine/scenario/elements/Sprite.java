package com.bascenario.engine.scenario.elements;

import lombok.Builder;

@Builder
public record Sprite(String skeleton, String atlas, String defaultAnimation, boolean fadeIn) {
}