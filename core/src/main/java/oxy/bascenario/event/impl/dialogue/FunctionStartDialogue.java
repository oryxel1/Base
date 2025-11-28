package oxy.bascenario.event.impl.dialogue;

import com.google.gson.JsonObject;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionStartDialogue extends FunctionEvent<StartDialogueEvent> {
    public FunctionStartDialogue(StartDialogueEvent event) {
        super(event);
    }

    @Override
    public void start(ScenarioScreen screen) {
        screen.getDialogueRenderer().start(event.getIndex(), event.getName(), event.getAssociation(), event.isBackground(), event.getDialogues());
        screen.setBusyDialogue(true);
    }

    @Override
    public void serialize(JsonObject serialized) {
        // TODO ....
    }

    @Override
    public StartDialogueEvent deserialize(JsonObject serialized) {
        return null; // TODO....
    }
}
