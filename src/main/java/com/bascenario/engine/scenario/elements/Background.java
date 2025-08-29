package com.bascenario.engine.scenario.elements;

import lombok.Builder;

@Builder
public record Background(String path, boolean fadeIn, boolean fadeOut) {
}