import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.elements.image.Image;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioPreviewScreen;

import static oxy.bascenario.Launcher.*;

public class ScenarioPreviewTest {
    public static void main(String[] args) {
        final Scenario.Builder builder = Scenario.builder();
        builder.title("Round 2: Jet Boat Rampage");
        builder.subtitle("Episode 3");
        builder.previewBackground(new Image(FileInfo.from("C:\\Users\\Computer\\BAAS\\Temporary\\BG_MainOffice_Night.jpg")));

        launch(new ScenarioPreviewScreen(builder.build()), false);
    }
}
