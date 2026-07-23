import oxy.base.api.Scenario;
import oxy.base.api.effects.Weather;
import oxy.base.api.event.SetWeatherEvent;
import oxy.base.api.event.background.SetBackgroundEvent;
import oxy.base.api.utils.FileInfo;
import oxy.base.screens.ScenarioScreen;
import oxy.base.utils.Launcher;

public class WeatherTest {
    public static void main(String[] args) {
        final Scenario.Builder scenario = new Scenario.Builder();
        scenario.add(0, new SetBackgroundEvent(FileInfo.internal("bg_cs_trinity_14_2.jpg"), 0));
        scenario.add(0, new SetWeatherEvent(Weather.SNOW));

        Launcher.launch(new ScenarioScreen(scenario.build()));
    }
}
