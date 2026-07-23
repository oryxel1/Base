package scenario;

import oxy.base.api.event.element.AddElementEvent;
import oxy.base.api.render.RenderLayer;
import oxy.base.api.render.elements.Preview;
import oxy.base.api.render.elements.text.font.FontType;
import oxy.base.api.utils.FileInfo;
import oxy.base.screens.ScenarioScreen;
import oxy.base.utils.Launcher;
import oxy.base.api.Scenario;

public class ScenarioPreviewTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.add(0, new AddElementEvent(0, new Preview(FontType.NotoSans, "Scenario Preview Test", "Episode: 1",
                FileInfo.internal("BG_BlackMarket.jpg")), RenderLayer.TOP));

        Launcher.launch(new ScenarioScreen(scenario.build()), true);
    }
}
