package oxy.bascenario.event.impl.sound;

import com.google.gson.JsonObject;
import oxy.bascenario.api.event.impl.sound.SoundEvent;
import oxy.bascenario.event.base.EventFunction;
import oxy.bascenario.managers.AudioManager;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionSoundEvent extends EventFunction<SoundEvent> {
    public FunctionSoundEvent(SoundEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        switch (event.getEvent()) {
            case STOP -> AudioManager.getInstance().stop(event.getId());
            case PAUSE -> AudioManager.getInstance().pause(event.getId());
            case RESUME -> AudioManager.getInstance().resume(event.getId());
        }
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.addProperty("id", event.getId());
        serialized.addProperty("event-type", event.getEvent().name());
    }

    @Override
    public SoundEvent deserialize(JsonObject serialized) {
        return new SoundEvent(serialized.get("id").getAsInt(), SoundEvent.Event.valueOf(serialized.get("event-type").getAsString()));
    }
}
