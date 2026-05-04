package oxy.bascenario.event.impl.utility;

import oxy.bascenario.api.event.api.Event;
import oxy.bascenario.api.event.utility.CheckForDialogueEvent;
import oxy.bascenario.event.EventRegistries;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionCheckForDialogue extends FunctionEvent<CheckForDialogueEvent> {
    public FunctionCheckForDialogue(CheckForDialogueEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        if (screen.getDialogueRenderer().getCurrentIndex() != event.index()) {
            return;
        }

        for (Event e : event().events()) {
            try {
                final FunctionEvent<?> function = EventRegistries.EVENT_TO_FUNCTION.get(e.getClass()).getDeclaredConstructor(e.getClass()).newInstance(e);
                function.run(screen);
            } catch (Exception ignored) {
            }
        }
    }
}
