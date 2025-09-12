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

public class PlaySoundEvent extends Event<PlaySoundEvent> {
    private final Sound sound;
    private final boolean loop;

    public PlaySoundEvent(Sound sound, boolean loop) {
        super(sound.fadeIn() > 0 ? sound.fadeIn() + 100 : 0);
        this.sound = sound;
        this.loop = loop;
    }

    @Override
    public void render(ScenarioScreen screen, long time, Matrix4fStack positionMatrix, WindowInterface window) {
        if (sound.fadeIn() > 0) {
            AudioManager.getInstance().fadeIn(this.sound.path(), this.sound.fadeIn(), this.loop, this.sound.maxVolume(), false);
        }
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (sound.fadeIn() <= 0) {
            AudioManager.getInstance().play(this.sound.path(), this.loop, this.sound.maxVolume(), false);
        }
    }

    @Override
    public PlaySoundEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject serialized = json.getAsJsonObject();
        return new PlaySoundEvent(GsonUtil.getGson().fromJson(serialized.get("sound"), Sound.class), serialized.get("loop").getAsBoolean());
    }

    @Override
    public JsonElement serialize(PlaySoundEvent src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject serialized = new JsonObject();
        serialized.add("sound", GsonUtil.toJson(src.sound));
        serialized.addProperty("loop", src.loop);
        return serialized;
    }
}
