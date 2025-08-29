import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.elements.Background;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScenarioSerializerTest {
    public static void main(String[] args) {
        Scenario scenario = Scenario.builder().name("Test Scenario")
                .previewBackground(new Background("test", false, false))
                .build();
    }
}
