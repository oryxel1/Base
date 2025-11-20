package oxy.bascenario.event.impl;

import com.google.gson.JsonObject;
import oxy.bascenario.api.effects.Sound;
import oxy.bascenario.api.event.impl.sound.PlaySoundEvent;
import oxy.bascenario.event.base.EventFunction;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.serializers.utils.GsonUtils;

public class FunctionPlaySoundEvent extends EventFunction<PlaySoundEvent> {
    public FunctionPlaySoundEvent(PlaySoundEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        AudioManager.getInstance().play(event.getSound());
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("sound", GsonUtils.toJson(event.getSound()));
    }

    @Override
    public PlaySoundEvent deserialize(JsonObject serialized) {
        return new PlaySoundEvent(GsonUtils.getGson().fromJson(serialized.get("sound"), Sound.class));
    }
}
