package oxy.bascenario.serializers.types.event.impl.dialogue.enums;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import oxy.bascenario.api.event.dialogue.enums.TextOffset;
import oxy.bascenario.serializers.base.Type;
import oxy.bascenario.serializers.types.element.ElementTypes;

public class TextOffsetType implements Type<TextOffset> {
    @Override
    public JsonElement write(TextOffset textOffset) {
        final JsonObject object = new JsonObject();
        object.add("type", ElementTypes.OFFSET_TYPE.write(textOffset.type()));
        object.addProperty("offset", textOffset.offset());
        return object;
    }

    @Override
    public TextOffset read(JsonElement element) {
        final JsonObject object = element.getAsJsonObject();
        return new TextOffset(ElementTypes.OFFSET_TYPE.read(object.get("type")), object.get("offset").getAsFloat());
    }

    @Override
    public void write(TextOffset textOffset, ByteBuf buf) {
        ElementTypes.OFFSET_TYPE.write(textOffset.type(), buf);
        buf.writeFloat(textOffset.offset());
    }

    @Override
    public TextOffset read(ByteBuf buf) {
        return new TextOffset(ElementTypes.OFFSET_TYPE.read(buf), buf.readFloat());
    }
}
