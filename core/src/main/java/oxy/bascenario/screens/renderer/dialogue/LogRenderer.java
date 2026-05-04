package oxy.bascenario.screens.renderer.dialogue;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.dialogue.enums.TextOffset;
import oxy.bascenario.api.render.elements.Dialogue;

public class LogRenderer extends DialogueRenderer {
    public LogRenderer(Scenario scenario) {
        super(scenario);
    }

    public final boolean start(int index, TextOffset offset, Dialogue... dialogues) {
        if (index != currentIndex) {
            return false;
        }

        this.offset = offset;

        this.stop();
        add(index, true, dialogues);
        return true;
    }

    @Override
    public float renderYOffset() {
        return 100;
    }

    @Override
    public float textYDistance() {
        return 80;
    }

    @Override
    public void renderBackground() {
    }

    @Override
    public void renderDetails() {
    }
}
