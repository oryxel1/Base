import com.bascenario.Launcher;
import com.bascenario.engine.scenario.Scenario;
import com.bascenario.render.editor.ScenarioEditorScreen;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ScenarioEditorTest {
    public static void main(String[] args) throws IOException {
        final String test = new String(Files.readAllBytes(new File("C:\\Users\\Computer\\Projects\\BAScenarioEngine\\run\\scenario\\hina-valentine.json").toPath()));

        final Scenario.Builder builder = Scenario.builder(Scenario.fromJson(test));
        Launcher.launch(new ScenarioEditorScreen(builder));
    }
}
