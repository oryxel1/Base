package oxy.bascenario.event.impl;

import oxy.bascenario.api.effects.Weather;
import oxy.bascenario.api.event.SetWeatherEvent;
import oxy.bascenario.event.base.FunctionEvent;
import oxy.bascenario.screens.ScenarioScreen;

public class FunctionSetWeather extends FunctionEvent<SetWeatherEvent> {
    public FunctionSetWeather(SetWeatherEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.setWeather(event.weather() == null ? Weather.CLEAR : event.weather());
    }
}
