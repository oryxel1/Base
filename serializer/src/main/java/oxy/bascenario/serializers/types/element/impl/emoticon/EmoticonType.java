package oxy.bascenario.serializers.types.element.impl.emoticon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.render.elements.emoticon.Emoticon;
import oxy.bascenario.serializers.types.element.ElementType;

public class EmoticonType implements ElementType<Emoticon> {
    @Override
    public String type() {
        return "emoticon";
    }

    @Override
    public JsonElement write(Emoticon emoticon) {
        final JsonObject object = new JsonObject();
        object.addProperty("duration", emoticon.duration());
        object.addProperty("type", emoticon.type().name());
        object.addProperty("sound", emoticon.sound());
        return object;
    }

    @Override
    public Emoticon read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new Emoticon(object.get("duration").getAsInt(), oxy.bascenario.api.render.elements.emoticon.EmoticonType.valueOf(object.get("type").getAsString()), object.get("sound").getAsBoolean());
    }

    @Override
    public void write(Emoticon emoticon, ByteBuf buf) {
        buf.writeInt(emoticon.duration());
        buf.writeByte(emoticon.type().ordinal());
        buf.writeBoolean(emoticon.sound());
    }

    @Override
    public Emoticon read(ByteBuf buf) {
        return new Emoticon(buf.readInt(), oxy.bascenario.api.render.elements.emoticon.EmoticonType.values()[buf.readByte()], buf.readBoolean());
    }
}
