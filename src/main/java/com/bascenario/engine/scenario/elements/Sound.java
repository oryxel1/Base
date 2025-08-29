package com.bascenario.engine.scenario.elements;

import lombok.Builder;

@Builder
public record Sound(String path, long fadeIn, long fadeOut) {
}