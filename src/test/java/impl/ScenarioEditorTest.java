package impl;

import oxy.bascenario.Base;
import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Easing;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.event.element.values.PositionElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Sprite;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.api.utils.math.Vec2;
import oxy.bascenario.editor.screen.ScenarioEditorScreen;
import oxy.bascenario.utils.Launcher;

public class ScenarioEditorTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.name("EditorTest");

        Launcher.launch(new ScenarioEditorScreen(scenario.build(), scenario), false);
    }
}
