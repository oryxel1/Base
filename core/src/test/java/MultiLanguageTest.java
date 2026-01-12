import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.dialogue.StartDialogueEvent;
import oxy.bascenario.api.event.element.AddElementEvent;
import oxy.bascenario.api.render.RenderLayer;
import oxy.bascenario.api.render.elements.Dialogue;
import oxy.bascenario.api.render.elements.Preview;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.Launcher;

public class MultiLanguageTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();

        scenario.add(true, 0, new StartDialogueEvent(FontType.NotoSans, 0, "Kayoko", "Problem Solver 68", true,
                Dialogue.builder().add("The quick brown fox jump over the lazy dog").build()));

        scenario.add(true, 0, new StartDialogueEvent(FontType.Gyeonggi, 0, "카요코", "문제 해결사 68", true,
                Dialogue.builder().add("다람쥐 헌 쳇바퀴에 타고파", FontType.Gyeonggi).build()));

        scenario.add(true, 0, new StartDialogueEvent(FontType.ShinMaruGo, 0, "カヨコ", "問題解決者68", true,
                Dialogue.builder().add("素早い茶色キツネが怠けた犬を飛び越えました", FontType.ShinMaruGo).build()));

        scenario.add(true, 0, new StartDialogueEvent(FontType.ChillRoundGothic, 0, "加代子", "问题解决者 68", true,
                Dialogue.builder().add("敏捷的棕色狐狸跳过懒惰的狗", FontType.ChillRoundGothic).build()));

        scenario.add(true, 0, new StartDialogueEvent(FontType.NotoSansTC, 0, "加代子", "問題解決者 68", true,
                Dialogue.builder().add("敏捷的棕色狐狸跳過了懶惰的狗", FontType.NotoSansTC).build()));

//        scenario.add(0, new AddElementEvent(0, new Preview(FontType.ShinMaruGo, "シナリオプレビューテスト", "エピソード: 1", null), RenderLayer.TOP));

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}
