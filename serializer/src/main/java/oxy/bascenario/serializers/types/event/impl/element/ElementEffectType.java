package oxy.bascenario.serializers.types.event.impl.element;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.effects.Effect;
import oxy.bascenario.api.event.element.ElementEffectEvent;
import oxy.bascenario.api.utils.math.Axis;
import oxy.bascenario.serializers.Types;
import oxy.bascenario.serializers.base.TypeWithName;

import java.util.ArrayList;
import java.util.List;

public class ElementEffectType implements TypeWithName<ElementEffectEvent> {
    @Override
    public String type() {
        return "element-effect";
    }

    @Override
    public JsonElement write(ElementEffectEvent event) {
        final JsonObject object = new JsonObject();
        object.addProperty("id", event.id());
        object.add("subId", Types.NULLABLE_INT.write(event.subId()));
        object.add("effect", Types.EFFECT_TYPE.write(event.effect()));
        object.addProperty("type", event.type().name());

        final JsonArray array = new JsonArray();
        for (Object o : event.values()) {
            if (o instanceof Number number) {
                array.add(number);
            } else if (o instanceof Axis axis) {
                array.add(Types.AXIS_TYPE.write(axis));
            }
        }
        object.add("values", array);
        return object;
    }

    @Override
    public ElementEffectEvent read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        final JsonArray array = element.getAsJsonArray();
        final List<Object> values = new ArrayList<>();
        for (JsonElement el : array) {
            final JsonPrimitive primitive = el.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                final Number number = el.getAsJsonPrimitive().getAsNumber();
                if (number instanceof Float) {
                    values.add(number.floatValue());
                } else if (number instanceof Double) {
                    values.add(number.doubleValue());
                } else if (number instanceof Integer) {
                    values.add(number.intValue());
                }
            } else if (primitive.isString()) {
                values.add(primitive.getAsString());
            }
        }

        return new ElementEffectEvent(object.get("id").getAsInt(), Types.NULLABLE_INT.read(object.get("subId")), Types.EFFECT_TYPE.read(object.get("effect")), ElementEffectEvent.Type.valueOf(object.get("type").getAsString()), values);
    }

    @Override
    public void write(ElementEffectEvent event, ByteBuf buf) {
        buf.writeInt(event.id());
        Types.NULLABLE_INT.write(event.subId(), buf);
        Types.EFFECT_TYPE.write(event.effect(), buf);
        buf.writeByte(event.type().ordinal());

        switch (event.effect()) {
            case BLUR -> buf.writeInt((Integer) event.values()[0]);
            case HOLOGRAM -> Types.AXIS_TYPE.write((Axis) event.values()[0], buf);
            case RAINBOW -> {
                Types.AXIS_TYPE.write((Axis) event.values()[0], buf);
                buf.writeFloat((Float) event.values()[1]);
            }
            case OUTLINE -> {
                buf.writeInt((Integer) event.values()[0]);
                buf.writeByte((Integer) event.values()[1]);
            }
        }
    }

    @Override
    public ElementEffectEvent read(ByteBuf buf) {
        int id = buf.readInt();
        Integer subId = Types.NULLABLE_INT.read(buf);
        Effect effect = Types.EFFECT_TYPE.read(buf);
        ElementEffectEvent.Type type = ElementEffectEvent.Type.values()[buf.readByte()];
        final Object[] values = switch (effect) {
            case BLUR -> new Object[] {buf.readInt()};
            case HOLOGRAM -> new Object[] {Types.AXIS_TYPE.read(buf)};
            case RAINBOW -> {
                final Axis axis = Types.AXIS_TYPE.read(buf);
                final float distance = buf.readFloat();
                yield new Object[] {axis, distance};
            }
            case OUTLINE -> new Object[] {buf.readInt(), (int) buf.readByte()};
        };

        return new ElementEffectEvent(id, subId, effect, type, values);
    }
}
