package scenario;

import oxy.base.api.Scenario;
import oxy.base.api.event.ShowButtonsEvent;
import oxy.base.api.event.background.SetBackgroundEvent;
import oxy.base.api.event.dialogue.ShowQuestionSelectionEvent;
import oxy.base.api.render.elements.text.font.FontType;
import oxy.base.api.utils.FileInfo;
import oxy.base.screens.ScenarioScreen;
import oxy.base.utils.Launcher;

import java.util.List;

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
