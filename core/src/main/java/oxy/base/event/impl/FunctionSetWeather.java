package oxy.base.event.impl;

import oxy.base.api.effects.Weather;
import oxy.base.api.event.SetWeatherEvent;
import oxy.base.event.base.FunctionEvent;
import oxy.base.screens.ScenarioScreen;

public class FunctionSetWeather extends FunctionEvent<SetWeatherEvent> {
    public FunctionSetWeather(SetWeatherEvent event) {
        super(event);
    }

    @Override
    public void run(ScenarioScreen screen) {
        screen.setWeather(event.weather() == null ? Weather.CLEAR : event.weather());
    }
}
