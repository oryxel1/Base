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
    public StopSoundEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject serialized = json.getAsJsonObject();
        return new StopSoundEvent(GsonUtil.getGson().fromJson(serialized.get("sound"), Sound.class), serialized.get("fade").getAsBoolean(), serialized.get("fade-duration").getAsLong());
    }

    @Override
    public JsonElement serialize(StopSoundEvent src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject serialized = new JsonObject();
        serialized.add("sound", GsonUtil.toJson(src.sound));
        serialized.addProperty("fade", src.fade);
        serialized.addProperty("fade-duration", src.fadeDuration);
        return serialized;
    }
}
