package com.bascenario.engine.scenario.elements;

import lombok.Builder;

import java.util.Map;

@Builder
public record DialogueOptions(Map<String, Integer> options) {
}