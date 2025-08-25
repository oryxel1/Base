import com.bascenario.engine.scenario.Scenario;

import java.util.ArrayList;

public class ScenarioSerializerTest {
    public static void main(String[] args) {
        Scenario scenario = Scenario.builder().name("Test Scenario")
                .previewBackground(new Scenario.Background("", "test", -1, -1))
                .build();

        scenario.getBackgrounds().add(new Scenario.Background("hiniature house", "really nice background", 0, -1));

        scenario.getDialogues().add(Scenario.Dialogue.builder()
                .time(5).dialogue("Hello world").name("potato").role("the real potato").playSpeed(10)
                .build());

        scenario.getTimestamps().add(Scenario.Timestamp.builder()
                .time(5).events(new ArrayList<>())
                .build());

        System.out.println(scenario.toJson());
    }
}
