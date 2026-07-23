package oxy.base.serializers.types.event.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import oxy.base.api.effects.Weather;
import oxy.base.api.event.SetWeatherEvent;
import oxy.base.serializers.base.TypeWithName;

public class SetWeatherType implements TypeWithName<SetWeatherEvent> {
    @Override
    public String type() {
        return "set-weather";
    }

    @Override
    public JsonElement write(SetWeatherEvent setWeatherEvent) {
        return new JsonPrimitive(setWeatherEvent.weather().name());
    }

    @Override
    public SetWeatherEvent read(JsonElement element) {
        return new SetWeatherEvent(Weather.valueOf(element.getAsString()));
    }
}
