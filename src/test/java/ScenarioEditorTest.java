import com.bascenario.Launcher;
import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.event.EventSerializer;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.render.editor.ScenarioEditorScreen;

public class ScenarioEditorTest {
    public static void main(String[] args) {
        final Scenario.Builder builder = Scenario.builder().name("Scenario Editor Test");
        for (Event<?> event : EventSerializer.getEventClassesToDummies().values()) {
            if (event.defaultEvent() == null) {
                continue;
            }
            builder.add(1, (Event<?>) event.defaultEvent());
        }

        Launcher.launch(new ScenarioEditorScreen(builder));
    }
}
