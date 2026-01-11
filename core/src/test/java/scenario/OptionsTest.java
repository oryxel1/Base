package scenario;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.Launcher;

import java.util.LinkedHashMap;

public class OptionsTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        {
            final LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
            map.put("Yes", 0);
            map.put("No", 0);
            scenario.add(0, new ShowOptionsEvent(FontType.NotoSans, map));
        }

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}
