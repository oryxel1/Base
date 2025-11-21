package oxy.bascenario.event.impl.dialogue;

import com.google.gson.JsonObject;
import oxy.bascenario.api.elements.Dialogue;
import oxy.bascenario.api.event.impl.dialogue.AddDialogueEvent;
import oxy.bascenario.event.base.EventFunction;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionAddDialogue extends EventFunction<AddDialogueEvent> {
    public FunctionAddDialogue(AddDialogueEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        for (Dialogue dialogue : event().getDialogues()) {
            screen.getDialogueRenderer().add(event.getIndex(), dialogue);
        }
        screen.setBusyDialogue(true);
    }

    @Override
    public void serialize(JsonObject serialized) {
        // TODO ....
    }

    @Override
    public AddDialogueEvent deserialize(JsonObject serialized) {
        return null; // TODO....
    }
}
