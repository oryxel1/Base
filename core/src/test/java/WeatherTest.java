import oxy.bascenario.api.Scenario;
import oxy.bascenario.api.effects.Weather;
import oxy.bascenario.api.event.SetWeatherEvent;
import oxy.bascenario.api.event.background.SetBackgroundEvent;
import oxy.bascenario.api.utils.FileInfo;
import oxy.bascenario.screens.ScenarioScreen;
import oxy.bascenario.utils.Launcher;

public class WeatherTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.add(0, new SetBackgroundEvent(FileInfo.internal("bg_cs_trinity_14_2.jpg"), 0));
        scenario.add(0, new SetWeatherEvent(Weather.SNOW));

        Launcher.launch(new ScenarioScreen(scenario.build()));
    }
}
