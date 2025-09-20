package com.bascenario.engine.scenario;

import com.bascenario.engine.scenario.event.api.Event;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public record Timestamp(@SerializedName("wait-for-dialogue") boolean waitForDialogue, long time, List<Event<?>> events) {
}