package oxy.bascenario.serializers.types.event.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import oxy.bascenario.api.effects.ScreenEffect;
import oxy.bascenario.api.event.ScreenEffectEvent;
import oxy.bascenario.serializers.base.TypeWithName;
import oxy.bascenario.serializers.types.primitive.EnumType;

public class ScreenEffectType implements TypeWithName<ScreenEffectEvent> {
    private final EnumType<ScreenEffectEvent.Type> type = new EnumType<>(ScreenEffectEvent.Type.class, ScreenEffectEvent.Type.values());
    private final EnumType<ScreenEffect> seType = new EnumType<>(ScreenEffect.class, ScreenEffect.values());

    @Override
    public JsonElement write(ScreenEffectEvent event) {
        final JsonObject object = new JsonObject();
        object.add("type", type.write(event.type()));
        object.add("effect", seType.write(event.effect()));
        return object;
    }

    @Override
    public ScreenEffectEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new ScreenEffectEvent(type.read(object.get("type")), seType.read(object.get("effect")));
    }

    @Override
    public String type() {
        return "screen-effect";
    }
}
