package com.bascenario.engine.scenario.event.impl.sound;

import com.bascenario.managers.AudioManager;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.render.ImGuiUtil;
import com.google.gson.*;
import imgui.ImGui;

public class StopSoundEvent extends Event<StopSoundEvent> {
    private int soundId;
    private long duration;

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
    public void renderImGui() {
        this.soundId = ImGuiUtil.inputInt("Sound ID", this.soundId);

        if (ImGui.checkbox("Fade Out", this.duration > 0)) {
            this.duration = this.duration <= 0 ? 1 : 0;
        }

        if (this.duration > 0) {
            this.duration = ImGuiUtil.sliderInt("Fade Duration", (int) this.duration, 0, 10000);
        }
    }

    @Override
    public StopSoundEvent defaultEvent() {
        return new StopSoundEvent(1, 1000L);
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
