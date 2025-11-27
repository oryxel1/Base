package oxy.bascenario.event.impl;

import com.google.gson.JsonObject;
import oxy.bascenario.api.event.impl.dialogue.ShowOptionsEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionShowOptions extends FunctionEvent<ShowOptionsEvent> {
    public FunctionShowOptions(ShowOptionsEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        screen.getOptionsRenderer().setOptions(event.getOptions());
        screen.setBusyOptions(true);
    }

    @Override
    public void serialize(JsonObject serialized) {
    }

    @Override
    public ShowOptionsEvent deserialize(JsonObject serialized) {
        return null; // TODO..
    }
}
