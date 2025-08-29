package com.bascenario.engine.scenario.event.impl.dialogue;

import com.bascenario.engine.scenario.elements.DialogueOptions;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.render.DialogueOptionsRender;
import com.bascenario.engine.scenario.screen.ScenarioScreen;

public class ShowDialogueOptionEvent extends Event {
    private final DialogueOptions options;
    public ShowDialogueOptionEvent(DialogueOptions options) {
        super(0);
        this.options = options;
    }

    @Override
    public void onStart(ScenarioScreen screen) {
        screen.setDialogueOptions(new DialogueOptionsRender(screen, this.options));
    }
}
