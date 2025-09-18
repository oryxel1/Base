package com.bascenario.engine.scenario.event.impl.sound;

import com.bascenario.managers.AudioManager;
import com.bascenario.engine.scenario.elements.Sound;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.scenario.ScenarioScreen;
import com.bascenario.util.GsonUtil;
import com.google.gson.*;

public class PlaySoundEvent extends Event<PlaySoundEvent> {
    private final Sound sound;
    private final boolean loop;

    public PlaySoundEvent(Sound sound, boolean loop) {
        super(sound.fadeIn() > 0 ? sound.fadeIn() + 100 : 0);
        this.sound = sound;
        this.loop = loop;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        if (sound.fadeIn() <= 0) {
            AudioManager.getInstance().play(this.sound.soundId(), this.sound.path(), this.loop, this.sound.maxVolume(), false);
        } else {
            AudioManager.getInstance().playFadeIn(this.sound.soundId(), this.sound.path(), this.sound.fadeIn(), this.loop, this.sound.maxVolume(), false);
        }
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("sound", GsonUtil.toJson(this.sound));
        serialized.addProperty("loop", this.loop);
    }

    @Override
    public PlaySoundEvent deserialize(JsonObject serialized) {
        Sound sound = GsonUtil.getGson().fromJson(serialized.get("sound"), Sound.class);
        sound = new Sound(sound.path(), sound.maxVolume(), sound.fadeIn(), Math.abs(sound.soundId()));

        return new PlaySoundEvent(sound, serialized.get("loop").getAsBoolean());
    }

    @Override
    public String type() {
        return "play-sound";
    }
}
