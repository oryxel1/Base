import com.bascenario.Launcher;
import com.bascenario.engine.scenario.Scenario;
import com.bascenario.engine.scenario.event.EventSerializer;
import com.bascenario.engine.scenario.event.api.Event;
import com.bascenario.engine.scenario.event.impl.QueueEventEvent;
import com.bascenario.render.editor.ScenarioEditorScreen;

import java.util.ArrayList;
import java.util.List;

public class ScenarioEditorTest {
    public static void main(String[] args) {
        final Scenario.Builder builder = Scenario.builder().name("Scenario Editor Test");
        List<Event<?>> events = new ArrayList<>();
        for (Event<?> event : EventSerializer.getEventClassesToDummies().values()) {
            if (event.defaultEvent() == null) {
                continue;
            }
            events.add((Event<?>) event.defaultEvent());
            events.add(new QueueEventEvent(100L, (Event<?>) event.defaultEvent()));
        }

        builder.add(1, events.toArray(new Event[0]));

        Launcher.launch(new ScenarioEditorScreen(builder));
    }
}
