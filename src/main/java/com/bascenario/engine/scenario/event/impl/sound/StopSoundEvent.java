package com.bascenario.engine.scenario.event.impl.sound;

import com.bascenario.managers.AudioManager;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.google.gson.*;

public class StopSoundEvent extends Event<StopSoundEvent> {
    private final int soundId;
    private final long duration;

    public StopSoundEvent(int soundId, long duration) {
        super(0);
        this.soundId = soundId;
        this.duration = duration;
    }

    public StopSoundEvent(int soundId) {
        super(0);
        this.soundId = soundId;
        this.duration = 0;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (this.duration <= 0) {
            AudioManager.getInstance().stop(this.soundId);
        } else {
            AudioManager.getInstance().stopFadeOut(this.soundId, this.duration);
        }
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("sound-id", this.soundId);
        serialized.addProperty("fade-duration", this.duration);
    }

    @Override
    public StopSoundEvent deserialize(JsonObject serialized) {
        return new StopSoundEvent(serialized.get("sound-id").getAsInt(), serialized.get("fade-duration").getAsLong());
    }

    @Override
    public String type() {
        return "stop-sound";
    }
}
