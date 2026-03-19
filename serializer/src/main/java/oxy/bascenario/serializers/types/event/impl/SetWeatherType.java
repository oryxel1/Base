package oxy.bascenario.serializers.types.event.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.effects.Weather;
import oxy.bascenario.api.event.SetWeatherEvent;
import oxy.bascenario.serializers.base.TypeWithName;

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

    @Override
    public void write(SetWeatherEvent setWeatherEvent, ByteBuf buf) {
        buf.writeInt(setWeatherEvent.weather().ordinal());
    }

    @Override
    public SetWeatherEvent read(ByteBuf buf) {
        return new SetWeatherEvent(Weather.values()[buf.readInt()]);
    }
}
