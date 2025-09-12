package com.bascenario.engine.scenario.event.impl.sound;

import com.bascenario.managers.AudioManager;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;
import net.raphimc.thingl.implementation.window.WindowInterface;
import org.joml.Matrix4fStack;

import java.lang.reflect.Type;

public class StopSoundEvent extends Event<StopSoundEvent> {
    private final Sound sound;
    private final boolean fade;
    private final long fadeDuration;

    public StopSoundEvent(Sound sound, boolean fade, long fadeDuration) {
        super(fadeDuration + 1000L);
        this.sound = sound;
        this.fade = fade;
        this.fadeDuration = fadeDuration;
    }

    public StopSoundEvent(Sound sound) {
        super(0);
        this.sound = sound;
        this.fade = false;
        this.fadeDuration = 0;
    }

    @Override
    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {
        if (this.fade) {
            AudioManager.getInstance().fadeOut(this.sound.path(), this.fadeDuration);
        }
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (!this.fade) {
            AudioManager.getInstance().stop(this.sound.path());
        }
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("sound", GsonUtil.toJson(this.sound));
        serialized.addProperty("fade", this.fade);
        serialized.addProperty("fade-duration", this.fadeDuration);
    }

    @Override
    public StopSoundEvent deserialize(JsonObject serialized) {
        return new StopSoundEvent(GsonUtil.getGson().fromJson(serialized.get("sound"), Sound.class), serialized.get("fade").getAsBoolean(), serialized.get("fade-duration").getAsLong());
    }

    @Override
    public String type() {
        return "stop-sound";
    }
}
