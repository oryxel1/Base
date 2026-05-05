package oxy.bascenario.api.event;

import oxy.bascenario.api.effects.Weather;
import oxy.bascenario.api.event.api.Event;

public record SetWeatherEvent(Weather weather) implements Event {
}
