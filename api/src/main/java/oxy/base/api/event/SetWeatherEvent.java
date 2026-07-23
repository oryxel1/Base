package oxy.base.api.event;

import oxy.base.api.effects.Weather;
import oxy.base.api.event.api.Event;

public record SetWeatherEvent(Weather weather) implements Event {
}
