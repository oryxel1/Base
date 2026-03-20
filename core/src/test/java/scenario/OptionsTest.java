package scenario;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.background.SetBackgroundEvent;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.Launcher;

import java.util.LinkedHashMap;
import java.util.Map;

public class OptionsTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.add(0, new SetBackgroundEvent(FileInfo.internal("BG_ShoppingMall.jpg"), 0));

        scenario.add(0, new ShowOptionsEvent(Map.of("The quick brown fox jump over the lazy dog", 0)));
        scenario.add(true, 1, new ShowOptionsEvent(Map.of("다람쥐 헌 쳇바퀴에 타고파", 0)));
        scenario.add(true, 1, new ShowOptionsEvent(Map.of("素早い茶色キツネが怠けた犬を飛び越えました", 0)));
        scenario.add(true, 1, new ShowOptionsEvent(Map.of("敏捷的棕色狐狸跳过懒惰的狗", 0)));
        scenario.add(true, 1, new ShowOptionsEvent(Map.of("敏捷的棕色狐狸跳過了懶惰的狗", 0)));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}
