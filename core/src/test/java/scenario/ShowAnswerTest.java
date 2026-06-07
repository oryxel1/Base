package scenario;

import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.event.ShowButtonsEvent;
import oxy.bascenario.api.event.background.SetBackgroundEvent;
import oxy.bascenario.api.event.dialogue.ShowOptionsEvent;
import oxy.bascenario.api.event.dialogue.ShowQuestionSelectionEvent;
import oxy.bascenario.api.render.elements.text.font.FontType;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.Launcher;

import java.util.List;
import java.util.Map;

public class ShowAnswerTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.add(0, new SetBackgroundEvent(FileInfo.internal("BG_ShoppingMall.jpg"), 0), new ShowButtonsEvent(true));

        scenario.add(true, 0, new ShowQuestionSelectionEvent(FontType.NotoSans,
                "How is this a question?",
                List.of(
                        new ShowQuestionSelectionEvent.Answer(0, "Why not?", false),
                        new ShowQuestionSelectionEvent.Answer(0, "Why yes?", false),
                        new ShowQuestionSelectionEvent.Answer(0, "Why why?", false)
                ))
        );

        Launcher.launch(new ScenarioScreen(scenario.build()), false);
    }
}
