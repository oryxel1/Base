package oxy.bascenario.event.impl;

import com.google.gson.JsonObject;
import oxy.bascenario.api.elements.image.Image;
import oxy.bascenario.api.event.impl.SetBackgroundEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.serializers.utils.GsonUtils;

public class FunctionSetBackground extends FunctionEvent<SetBackgroundEvent> {
    public FunctionSetBackground(SetBackgroundEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        screen.setBackground(event.getBackground());
    }

    @Override
    public void serialize(JsonObject serialized) {
        serialized.add("background", GsonUtils.toJson(event.getBackground()));
    }

    @Override
    public SetBackgroundEvent deserialize(JsonObject serialized) {
        return new SetBackgroundEvent(GsonUtils.getGson().fromJson(serialized.get("background"), Image.class));
    }
}
