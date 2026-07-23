package scenario;

import oxy.base.api.Scenario;
import oxy.base.api.event.ShowButtonsEvent;
import oxy.base.api.event.background.SetBackgroundEvent;
import oxy.base.api.event.dialogue.ShowOptionsEvent;
import oxy.base.api.render.elements.text.font.FontType;
import oxy.base.api.utils.FileInfo;
import oxy.base.screens.ScenarioScreen;
import oxy.base.utils.Launcher;

import java.util.Map;

public class OptionsTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.add(0, new SetBackgroundEvent(FileInfo.internal("BG_ShoppingMall.jpg"), 0), new ShowButtonsEvent(true));
//        scenario.add(true, 1, new ShowOptionsEvent(Map.of("   ", 0, "        ", 0)));

        scenario.add(true, 0, new ShowOptionsEvent(Map.of("\"By the way, what did you bring?\"", 0)));
        scenario.add(true, 0, new ShowOptionsEvent(Map.of("\"Huh?\"", 0, "\"This is a multiple dialogue choice.\"", 0)));

//        scenario.add(true, 1, new ShowOptionsEvent(Map.of("Insanely long option just to test how this actually looks. Yes this should works lol ABCDXYZOD721SDFDSFDFS.", 0)));
        scenario.add(true, 0, new ShowOptionsEvent(Map.of("The quick brown fox jump over the lazy dog", 0)));
        scenario.add(true, 1, new ShowOptionsEvent(FontType.Gyeonggi, Map.of("다람쥐 헌 쳇바퀴에 타고파", 0)));
        scenario.add(true, 1, new ShowOptionsEvent(FontType.ShinMaruGo, Map.of("素早い茶色キツネが怠けた犬を飛び越えました", 0)));
        scenario.add(true, 1, new ShowOptionsEvent(FontType.ChillRoundGothic, Map.of("敏捷的棕色狐狸跳过懒惰的狗", 0)));
        scenario.add(true, 1, new ShowOptionsEvent(FontType.NotoSansTC, Map.of("敏捷的棕色狐狸跳過了懶惰的狗", 0)));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}
