import com.bascenario.engine.scenario.Scenario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScenarioSerializerTest {
    public static void main(String[] args) {
        Scenario scenario = Scenario.builder().name("Test Scenario")
                .previewBackground(new Scenario.Background("test", -1, -1, false, false))
                .build();

        scenario.getBackgrounds().add(new Scenario.Background("really nice background", 0, -1, true, true));

        scenario.getDialogues().put(0, List.of(Scenario.Dialogue.builder()
                .time(5).dialogue("Hello world").name("potato").role("the real potato").playSpeed(10)
                .build()));

        scenario.getDialogueOptions().add(Scenario.DialogueOptions.builder().options(
                Map.of("hiniature hair", 0, "potato", 0)
        ).time(10L).build());

        scenario.getTimestamps().add(Scenario.Timestamp.builder()
                .time(5).events(new ArrayList<>())
                .build());

        System.out.println(scenario.toJson());
    }
}
