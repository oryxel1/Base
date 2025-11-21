package oxy.bascenario.event.impl.dialogue;

import com.google.gson.JsonObject;
import oxy.bascenario.api.event.impl.dialogue.CloseDialogueEvent;
import oxy.bascenario.api.event.impl.dialogue.StartDialogueEvent;
import oxy.bascenario.event.base.EventFunction;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionCloseDialogue extends EventFunction<CloseDialogueEvent> {
    public FunctionCloseDialogue(CloseDialogueEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        screen.getDialogueRenderer().stop();

    }

    @Override
    public void serialize(JsonObject serialized) {
    }

    @Override
    public CloseDialogueEvent deserialize(JsonObject serialized) {
        return new CloseDialogueEvent();
    }
}
